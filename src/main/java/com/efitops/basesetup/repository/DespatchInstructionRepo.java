package com.efitops.basesetup.repository;

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
	
	//schedule no dropdown
	
	    @Query(value = """
	        SELECT
	            sds.sales_delivery_schedule_id,
	            sds.dlv_no,
	            sdsd.sales_delivery_schedule_details_id,
	            i.item_id,
	            i.item_code,
	            i.item_description,
	            sdsd.actual_planned_qty,
	            COALESCE(SUM(dd.descQty), 0) AS dispatched_qty,
	            (sdsd.actual_planned_qty - COALESCE(SUM(dd.descQty), 0)) AS balance_qty

	        FROM sales_delivery_schedule_details sdsd

	        INNER JOIN sales_delivery_schedule sds
	            ON sds.sales_delivery_schedule_id = sdsd.sales_delivery_schedule_id

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
	            sds.sales_delivery_schedule_id,
	            sds.dlv_no,
	            sdsd.sales_delivery_schedule_details_id,
	            i.item_id,
	            i.item_code,
	            i.item_description,
	            sdsd.actual_planned_qty

	        HAVING
	            (sdsd.actual_planned_qty - COALESCE(SUM(dd.descQty),0)) > 0
	        """, nativeQuery = true)
	    List<Object[]> getDespatchScheduleNo(@Param("scheduleNo") String scheduleNo,@Param("branch") Long branch,@Param("orgId") Long orgId);
	   
	    
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
	    	            a.sales_delivery_schedule_id,
	    	            a.month_of_schedule
	    	        FROM sales_delivery_schedule a
	    	        INNER JOIN sales_delivery_schedule_details d
	    	            ON a.sales_delivery_schedule_id = d.sales_delivery_schedule_id
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
	}

