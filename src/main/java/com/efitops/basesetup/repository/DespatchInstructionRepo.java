package com.efitops.basesetup.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
			ORDER BY despatch_basic_id
			""", nativeQuery = true)
	List<DespatchInstructionVO> getDespatchInstructionByOrgId(@Param("orgId") Long orgId, @Param("branch") Long branch);

	// despatch schedule no dropdown

//         @Query(value = "(\r\n"
//			+ "SELECT DISTINCT\r\n"
//			+ "    sb.sdvbasic_id,\r\n"
//			+ "    sb.dlv_no,\r\n"
//			+ "    sb.dlv_date,\r\n"
//			+ "    sb.month_of_schedule,\r\n"
//			+ "    sd.invoicetype,\r\n"
//			+ "    3 AS SN\r\n"
//			+ "FROM sdvbasic sb\r\n"
//			+ "INNER JOIN sdvdet sd\r\n"
//			+ "    ON sb.sdvbasic_id = sd.sdvbasic_id\r\n"
//			+ "INNER JOIN sales_contract_basic scb\r\n"
//			+ "    ON scb.doc_id = sd.so_no_contractno\r\n"
//			+ "INNER JOIN customer_header c\r\n"
//			+ "    ON sb.customer_id = c.customer_id\r\n"
//			+ "WHERE sb.cancel = FALSE\r\n"
//			+ "  AND c.customer_id = ?1\r\n"
//			+ "  AND sb.branch_id = ?3\r\n"
//			+ "  AND sb.org_id = ?4\r\n"
//			+ "  AND NOT EXISTS (\r\n"
//			+ "        SELECT 1\r\n"
//			+ "        FROM despatch_basic db\r\n"
//			+ "        INNER JOIN despatch_detail dd\r\n"
//			+ "            ON db.despatch_basic_id = dd.despatch_basic_id\r\n"
//			+ "        WHERE db.cancel = FALSE\r\n"
//			+ "          AND db.schdule_no = sb.dlv_no\r\n"
//			+ "          AND db.custumer = c.customer_id\r\n"
//			+ "          AND dd.item = sd.item_id\r\n"
//			+ "  )\r\n"
//			+ ")\r\n"
//			+ "\r\n"
//			+ "UNION\r\n"
//			+ "\r\n"
//			+ "(\r\n"
//			+ "SELECT DISTINCT\r\n"
//			+ "    sb.sdvbasic_id,\r\n"
//			+ "    sb.dlv_no,\r\n"
//			+ "    sb.dlv_date,\r\n"
//			+ "    sb.month_of_schedule,\r\n"
//			+ "    sd.invoicetype,\r\n"
//			+ "    6 AS SN\r\n"
//			+ "FROM sdvbasic sb\r\n"
//			+ "INNER JOIN sdvdet sd\r\n"
//			+ "    ON sb.sdvbasic_id = sd.sdvbasic_id\r\n"
//			+ "INNER JOIN customer_header ch\r\n"
//			+ "    ON sb.customer_id = ch.customer_id\r\n"
//			+ "INNER JOIN order_acceptance_basic ob\r\n"
//			+ "    ON sd.so_no_contractno = ob.doc_id\r\n"
//			+ "WHERE sb.cancel = FALSE\r\n"
//			+ "  AND ch.customer_id = ?1\r\n"
//			+ "  AND sb.month_year = ?2\r\n"
//			+ "  AND sb.branch_id =?3\r\n"
//			+ "  AND sb.org_id = ?4\r\n"
//			+ "  AND NOT EXISTS (\r\n"
//			+ "        SELECT 1\r\n"
//			+ "        FROM despatch_basic x\r\n"
//			+ "        INNER JOIN despatch_detail y\r\n"
//			+ "            ON x.despatch_basic_id = y.despatch_basic_id\r\n"
//			+ "        WHERE x.cancel = FALSE\r\n"
//			+ "          AND x.schdule_no = ob.doc_id\r\n"
//			+ "          AND x.custumer = ch.customer_id\r\n"
//			+ "          AND y.item = sd.item_id\r\n"
//			+ "  )\r\n"
//			+ ")\r\n"
//			+ "ORDER BY dlv_no", nativeQuery = true)
//	List<Object[]> getScheduleNoDropdownForDespatchInstruction(@Param("customer") Long customer, @Param("monthYear") String monthYear,
//			@Param("branch") Long branch, @Param("orgId") Long orgId);

         @Query(value = """
			(
			SELECT DISTINCT
			    sb.sdvbasic_id,
			    sb.dlv_no,
			    sb.dlv_date,
			    sb.month_of_schedule,
			    sd.invoicetype

			FROM sdvbasic sb
			INNER JOIN sdvdet sd
			    ON sb.sdvbasic_id = sd.sdvbasic_id
			INNER JOIN sales_contract_basic scb
			    ON scb.doc_id = sd.so_nocontractno
			INNER JOIN customer_header c
			    ON sb.customer_id = c.customer_id
			WHERE sb.cancel = FALSE
			  AND c.customer_id = :customer
			  AND sb.branch_id = :branch
			  AND sb.org_id = :orgId
			  AND NOT EXISTS (
			        SELECT 1
			        FROM despatch_basic db
			        INNER JOIN despatch_detail dd
			            ON db.despatch_basic_id = dd.despatch_basic_id
			        WHERE db.cancel = FALSE
			          AND db.schdule_no = sb.dlv_no
			          AND db.custumer = c.customer_id
			          AND dd.item = sd.item_id
			  )
			)

			UNION

			(
			SELECT DISTINCT
			    sb.sdvbasic_id,
			    sb.dlv_no,
			    sb.dlv_date,
			    sb.month_of_schedule,
			    sd.invoicetype

			FROM sdvbasic sb
			INNER JOIN sdvdet sd
			    ON sb.sdvbasic_id = sd.sdvbasic_id
			INNER JOIN customer_header ch
			    ON sb.customer_id = ch.customer_id
			INNER JOIN order_acceptance_basic ob
			    ON sd.so_nocontractno = ob.doc_id
			WHERE sb.cancel = FALSE
			  AND ch.customer_id = :customer
			  AND sb.month_year = :monthYear
			  AND sb.branch_id = :branch
			  AND sb.org_id = :orgId
			  AND NOT EXISTS (
			        SELECT 1
			        FROM despatch_basic x
			        INNER JOIN despatch_detail y
			            ON x.despatch_basic_id = y.despatch_basic_id
			        WHERE x.cancel = FALSE
			          AND x.schdule_no = ob.doc_id
			          AND x.custumer = ch.customer_id
			          AND y.item = sd.item_id
			  )
			)

			ORDER BY dlv_no
			""", nativeQuery = true)
	List<Object[]> getScheduleNoDropdownForDespatchInstruction(@Param("customer") Long customer,
			@Param("monthYear") String monthYear, @Param("branch") Long branch, @Param("orgId") Long orgId);

	@Query(value = """
			SELECT
			    ob.doc_id,
			    ob.doc_date,
			    ob.order_acceptance_basic_id AS id,
			    NULL AS type
			FROM order_acceptance_basic ob
			INNER JOIN customer_header ch
			    ON ch.customer_id = ob.customer
			WHERE ob.cancel = FALSE
			  AND ch.customer_id = :customer
			  AND ob.branch = :branch
			  AND ob.org_id = :orgId

			UNION

			SELECT
			    sc.doc_id,
			    sc.doc_date,
			    sc.salescontract_id AS id,
			    sc.invoice_type AS type
			FROM sales_contract_basic sc
			INNER JOIN customer_header ch
			    ON ch.customer_id = sc.customer
			WHERE sc.cancel = FALSE
			  AND ch.customer_id = :customer
			  AND sc.branch = :branch
			  AND sc.org_id = :orgId

			ORDER BY doc_date
			""", nativeQuery = true)
	List<Object[]> getOrderAndSalesContractDropdownFromDespatchInstruction(@Param("customer") Long customerId,
			@Param("branch") Long branch, @Param("orgId") Long orgId);

	// Despatch Schedule month dropdown
	@Query(value = """
			SELECT
			    a.sdvbasic_id,
			    a.month_of_schedule
			FROM sdvbasic a
			INNER JOIN sdvdet d
			    ON a.sdvbasic_id = d.sdvbasic_id
			WHERE a.cancel = FALSE
			  AND d.item_id = :item
			  AND a.dlv_no = :dlvNo
			  AND a.branch_id = :branch
			  AND a.org_id = :orgId
			GROUP BY
			    a.sdvbasic_id,
			    a.month_of_schedule
			""", nativeQuery = true)
	List<Object[]> getScheduleMonthForDespatchInstruction(@Param("item") Long item, @Param("dlvNo") String dlvNo,
			@Param("branch") Long branch, @Param("orgId") Long orgId);

	// planned qty
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
			   AND dd.item = sdsd.item_id

			WHERE sds.cancel = FALSE
			  AND i.item_id = :item
			  AND sds.branch_id = :branch
			  AND sds.org_id = :orgId
			""", nativeQuery = true)
	BigDecimal getPlannedQtyForDespatchInstruction(@Param("item") Long item, @Param("branch") Long branch,
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

	// despatch fill grid api
	@Query(value = """
			SELECT
			    i.item_id,
			    '' AS mark,
			    i.item_code,
			    i.item_description,
			    u.unitmaster_id,
			   
			    sb.sdvbasic_id,
			    sd.so_no_contractno,
			    2 AS SN,
			     u.unit_id
			FROM sdvbasic sb
			INNER JOIN sdvdet sd
			    ON sb.sdvbasic_id = sd.sdvbasic_id
			INNER JOIN item i
			    ON sd.item_id = i.item_id
			INNER JOIN unitmaster u
			    ON i.primary_unit = u.unitmaster_id
			INNER JOIN customer_header ch
			    ON sb.customer_id = ch.customer_id
			WHERE sb.cancel = FALSE
			  AND i.cancel = FALSE
			  AND ch.customer_id = :customerId
			  AND sb.sdvbasic_id = :sdvBasicId
			  AND sb.branch_id = :branch
			  AND sb.org_id = :orgId
			  AND NOT EXISTS (
			        SELECT 1
			        FROM despatch_basic db
			        INNER JOIN despatch_detail dd
			            ON db.despatch_basic_id = dd.despatch_basic_id
			        WHERE db.cancel = FALSE
			          AND db.schdule_no = sb.dlv_no
			          AND dd.item = sd.item_id
			  )
			ORDER BY i.item_code
			""", nativeQuery = true)
	List<Object[]> getFillGridItemsForDespatchInstruction(@Param("customerId") Long customerId,
			@Param("sdvBasicId") Long sdvBasicId, @Param("branch") Long branch, @Param("orgId") Long orgId);

	@Query(value = """
	        SELECT
	            db.doc_id,
	            db.doc_date,
	            db.schdule_no,
	            db.schdule_date
	        FROM despatch_basic db
	        WHERE db.custumer = ?1
	          AND db.org_id = ?2
	          AND db.branch = ?3 and active=1 and cancel=0
	          AND NOT EXISTS (
	              SELECT 1
	              FROM sales_rejection_invoice_basic sri
	              WHERE sri.dispatch_instruction_no = db.doc_id
	                AND sri.customer = ?1
	                AND sri.org_id = ?2
	                AND sri.branch = ?3
	                AND sri.doc_type = ?4 and active=1 and cancel=0
	          )
	        """, nativeQuery = true)
	List<Object[]> getDespatchInstructionNoforSalesRejectionInv(
	        Long customer,
	        Long orgId,
	        Long branch,
	        String docType);

	@Query(value = """
	        SELECT
	            i.item_id,
	            i.item_code,
	            i.item_description,
	            h.hsn,
	            i.customer_part_no,
	            u.unitmaster_id,
	            u.unit_id,
	            gr.gstratemaster_id,
	            gr.cgst,
	            gr.sgst,
	            gr.igst,
	            dd.ord_accp_contr_no,
	            dd.desc_qty,

	            (
	                SELECT x.new_rate
	                FROM (

	                    /* Sales Contract Amendment */
	                    SELECT
	                        scad.new_rate,
	                        scad.new_validdate AS rate_date
	                    FROM sales_contract_amendment_basic scab
	                    INNER JOIN sales_contract_amendment_detail scad
	                        ON scad.sales_contract_amendment_basic_id =
	                           scab.sales_contract_amendment_basic_id
	                    WHERE scab.doc_id = dd.ord_accp_contr_no
	                      AND scad.item = dd.item
	                      AND scab.org_id = db.org_id
	                      AND scab.branch = db.branch
	                      AND scab.active = 1
	                      AND scab.cancel = 0
	                      AND scad.new_validdate IS NOT NULL

	                    UNION ALL

	                    /* Sales Contract */
	                    SELECT
	                        scd.order_rate AS new_rate,
	                        scd.effective_from AS rate_date
	                    FROM sales_contract_basic scb
	                    INNER JOIN sales_contract_detail scd
	                        ON scd.salescontract_id =
	                           scb.salescontract_id
	                    WHERE scb.doc_id = dd.ord_accp_contr_no
	                      AND scd.item = dd.item
	                      AND scb.org_id = db.org_id
	                      AND scb.branch = db.branch
	                      AND scb.active = 1
	                      AND scb.cancel = 0
	                      AND scd.effective_from IS NOT NULL

	                    UNION ALL

	                    /* Order Acceptance */
	                    SELECT
	                        oad.order_rate AS new_rate,
	                        oab.doc_date AS rate_date
	                    FROM order_acceptance_basic oab
	                    INNER JOIN order_acceptance_detail oad
	                        ON oad.order_acceptance_basic_id =
	                           oab.order_acceptance_basic_id
	                    WHERE oab.doc_id = dd.ord_accp_contr_no
	                      AND oad.item = dd.item
	                      AND oab.org_id = db.org_id
	                      AND oab.branch = db.branch
	                      AND oab.active = 1
	                      AND oab.cancel = 0
	                      AND oab.doc_date IS NOT NULL

	                    UNION ALL

	                    /* Sales Order Amendment */
	                    SELECT
	                        soad.new_rate,
	                        soad.new_delivery_date AS rate_date
	                    FROM sales_order_amendment_basic soa
	                    INNER JOIN sales_order_amendment_detail soad
	                        ON soad.sales_order_amendment_id =
	                           soa.sales_order_amendment_id
	                    WHERE soa.doc_id = dd.ord_accp_contr_no
	                      AND soad.item = dd.item
	                      AND soa.org_id = db.org_id
	                      AND soa.branch = db.branch
	                      AND soa.active = 1
	                      AND soa.cancel = 0
	                      AND soad.new_delivery_date IS NOT NULL

	                ) x
	                ORDER BY x.rate_date DESC
	                LIMIT 1
	            ) AS new_rate

	        FROM despatch_basic db

	        INNER JOIN despatch_detail dd
	            ON db.despatch_basic_id = dd.despatch_basic_id

	        INNER JOIN item i
	            ON i.item_id = dd.item

	        INNER JOIN unitmaster u
	            ON u.unitmaster_id = i.primary_unit

	        INNER JOIN hsn h
	            ON h.hsn_id = i.hsn_code

	        LEFT JOIN gstratemaster gr
	            ON gr.hsn_sac_code = h.hsn_id
	            AND gr.active = 1
	            AND gr.cancel = 0
	            AND gr.org_id = db.org_id
	            AND gr.branch = db.branch

	        WHERE db.doc_id = ?1
	          AND db.org_id = ?2
	          AND db.branch = ?3
	          AND db.active = 1
	          AND db.cancel = 0

	        ORDER BY i.item_code
	        """, nativeQuery = true)
	Set<Object[]> getItemDetailsforSalesRejectionInvoice(
	        String docId,
	        Long orgId,
	        Long branch);

}
