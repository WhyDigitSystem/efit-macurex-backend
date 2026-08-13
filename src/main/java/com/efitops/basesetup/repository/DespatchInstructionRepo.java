package com.efitops.basesetup.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.DespatchInstructionVO;

public interface DespatchInstructionRepo extends JpaRepository<DespatchInstructionVO, Long> {

	
 
	@Query(value = """
	        SELECT *
	        FROM despatch_basic
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = false
	          AND active = 1
	        ORDER BY di_no
	        """, nativeQuery = true)
	List<DespatchInstructionVO> getDespatchInstructionByOrgId(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
	//despatch schedule no dropdown
	@Query(value = """
		    SELECT
		        sds.sdvbasic_id,
		        sds.dlv_no,
		        sds.dlv_date,
		        sdsd.invoicetype,
		        COALESCE(SUM(dd.desc_qty), 0) AS dispatched_qty,
		        (sdsd.actual_planned_qty - COALESCE(SUM(dd.desc_qty), 0)) AS balance_qty

		    FROM sdvdet sdsd

		    INNER JOIN sdvbasic sds
		        ON sds.sdvbasic_id = sdsd.sdvbasic_id

		    INNER JOIN item i
		        ON i.item_id = sdsd.item_id

		    LEFT JOIN despatch_basic db
		        ON db.schdule_no = sds.dlv_no

		    LEFT JOIN despatch_detail dd
		        ON dd.despatch_basic_id = db.despatch_basic_id
		       AND dd.item_id = sdsd.item_id

		    WHERE sds.dlv_no = :scheduleNo
		      AND sds.branch_id = :branch
		      AND sds.org_id = :orgId

		    GROUP BY
		        sds.sdvbasic_id,
		        sds.dlv_no,
		        sds.dlv_date,
		        sdsd.invoicetype,
		        dd.desc_qty,
		        sdsd.actual_planned_qty

		    HAVING
		        (sdsd.actual_planned_qty - COALESCE(SUM(dd.desc_qty), 0)) > 0
		    """, nativeQuery = true)
		List<Object[]> getDespatchScheduleNo(
		        @Param("scheduleNo") String scheduleNo,
		        @Param("branch") Long branch,
		        @Param("orgId") Long orgId);
	   
	    
	    //despatch despatch salessontract no
	    @Query(value = """
	    	    SELECT
	    	        sc.customer_contract_no,
	    	        sc.contract_date,
	    	        sc.salescontract_id,
	    	        sc.invoice_type
	    	    FROM sales_contract_basic sc
	    	    INNER JOIN customer_header ch
	    	        ON ch.customer_id = sc.customer
	    	    WHERE sc.cancel = false
	    	      AND ch.customer_id = :customerId
	    	      AND sc.branch = :branch
	    	      AND sc.org_id = :orgId
	    	    ORDER BY sc.contract_date
	    	    """, nativeQuery = true)
	    	List<Object[]> getDespatchSalesContract(
	    	        @Param("customerId") Long customerId,
	    	        @Param("branch") Long branch,
	    	        @Param("orgId") Long orgId);
	    	
	    	//Despatch Schedule month dropdown
	    	@Query(value = """
	    	        SELECT
	    	            a.sdvbasic_id,
	    	            a.month_of_schedule
	    	        FROM sdvbasic a
	    	        INNER JOIN sdvdet d
	    	            ON a.sdvbasic_id = d.sdvbasic_id
	    	        INNER JOIN item i
	    	            ON d.item_id = i.item_id
	    	        WHERE a.cancel = false
	    	          AND i.item_id = :itemId
	    	          AND a.branch_id = :branch
	    	          AND a.org_id = :orgId
	    	        ORDER BY a.month_of_schedule
	    	        """, nativeQuery = true)
	    	List<Object[]> getDespatchScheduleMonth(
	    	        @Param("itemId") Long itemId,
	    	        @Param("branch") Long branch,
	    	        @Param("orgId") Long orgId);
	    	
	    	//planned qty
	    	@Query(value = """
	    		    SELECT
	    		        SUM(sdsd.actual_planned_qty) - COALESCE(SUM(dd.desc_qty), 0) AS plannedQty

	    		    FROM sdvbasic sds

	    		    INNER JOIN sdvdet sdsd
	    		        ON sds.sdvbasic_id = sdsd.sdvbasic_id

	    		    INNER JOIN item i
	    		        ON i.item_id = sdsd.item_id

	    		    LEFT JOIN despatch_basic db
	    		        ON db.schdule_no = sds.dlv_no

	    		    LEFT JOIN despatch_detail dd
	    		        ON dd.despatch_basic_id = db.despatch_basic_id
	    		       AND dd.item_id = sdsd.item_id

	    		    WHERE sds.cancel = FALSE
	    		      AND i.item_id = :itemId
	    		      AND sds.branch_id = :branch
	    		      AND sds.org_id = :orgId
	    		    """, nativeQuery = true)
	    		BigDecimal getDespatchPlannedQty(
	    		        @Param("itemId") Long itemId,
	    		        @Param("branch") Long branch,
	    		        @Param("orgId") Long orgId);
	    	
			// Pending qty
	    	
//	    	@Query(value = """
//
//	    			SELECT
//	    			    sdsd.actual_planned_qty AS pendingQty,
//	    			    1 AS sno
//	    			FROM sales_delivery_schedule sds
//
//	    			INNER JOIN customer_header ch
//	    			    ON ch.customer_id = sds.customer_id
//
//	    			INNER JOIN sales_delivery_schedule_details sdsd
//	    			    ON sds.sales_delivery_schedule_id = sdsd.sales_delivery_schedule_id
//
//	    			INNER JOIN item i
//	    			    ON i.item_id = sdsd.item_id
//
//	    			WHERE sds.cancel = FALSE
//	    			  AND sdsd.actual_planned_qty > 0
//	    			  AND i.item_id = :itemId
//	    			  AND sds.month_of_schedule = :month
//	    			  AND sds.branch_id = :branch
//	    			  AND sds.org_id = :orgId
//	    			  AND ch.customer_id = :customerId
//	    			  AND NOT EXISTS
//	    			  (
//	    			      SELECT 1
//	    			      FROM despatch_basic db
//	    			      INNER JOIN despatch_detail dd
//	    			          ON db.despatch_basic_id = dd.despatch_basic_id
//	    			      WHERE db.cancel = FALSE
//	    			        AND db.schdule_no = sds.dlv_no
//	    			        AND dd.item_id = i.item_id
//	    			  )
//
//	    			UNION
//
//	    			SELECT
//	    			    (sdsd.actual_planned_qty - SUM(dd.desc_qty)) AS pendingQty,
//	    			    2 AS sno
//	    			FROM sales_delivery_schedule sds
//
//	    			INNER JOIN customer_header ch
//	    			    ON ch.customer_id = sds.customer_id
//
//	    			INNER JOIN sales_delivery_schedule_details sdsd
//	    			    ON sds.sales_delivery_schedule_id = sdsd.sales_delivery_schedule_id
//
//	    			INNER JOIN item i
//	    			    ON i.item_id = sdsd.item_id
//
//	    			INNER JOIN despatch_basic db
//	    			    ON db.schdule_no = sds.dlv_no
//
//	    			INNER JOIN despatch_detail dd
//	    			    ON dd.despatch_basic_id = db.despatch_basic_id
//	    			   AND dd.item_id = i.item_id
//
//	    			WHERE sds.cancel = FALSE
//	    			  AND i.item_id = :itemId
//	    			  AND sds.month_of_schedule = :month
//	    			  AND sds.branch_id = :branch
//	    			  AND sds.org_id = :orgId
//	    			  AND ch.customer_id = :customerId
//
//	    			GROUP BY
//	    			    sdsd.actual_planned_qty
//
//	    			HAVING
//	    			    (sdsd.actual_planned_qty - SUM(dd.desc_qty)) > 0
//
//	    			UNION
//
//	    			SELECT
//	    			    sdsd.actual_planned_qty AS pendingQty,
//	    			    3 AS sno
//	    			FROM sales_delivery_schedule sds
//
//	    			INNER JOIN customer_header ch
//	    			    ON ch.customer_id = sds.customer_id
//
//	    			INNER JOIN sales_delivery_schedule_details sdsd
//	    			    ON sds.sales_delivery_schedule_id = sdsd.sales_delivery_schedule_id
//
//	    			INNER JOIN item i
//	    			    ON i.item_id = sdsd.item_id
//
//	    			WHERE sds.cancel = FALSE
//	    			  AND i.item_id = :itemId
//	    			  AND sds.month_of_schedule = :month
//	    			  AND sds.org_id = :orgId
//	    			  AND ch.customer_id = :customerId
//
//	    			""", nativeQuery = true)
//	    			List<Object[]> getDespatchPendingQty(
//	    			        @Param("itemId") Long itemId,
//	    			        @Param("month") String month,
//	    			        @Param("branch") Long branch,
//	    			        @Param("orgId") Long orgId,
//	    			        @Param("customerId") Long customerId);
	}

