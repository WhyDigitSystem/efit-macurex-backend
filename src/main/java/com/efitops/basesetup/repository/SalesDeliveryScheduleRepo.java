package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.SalesDeliveryScheduleVO;

public interface SalesDeliveryScheduleRepo extends JpaRepository<SalesDeliveryScheduleVO, Long> {

	@Query(value = """
			SELECT *
			FROM sdvbasic
			WHERE org_id = :orgId
			  AND branch = :branch
			  AND cancel = 0
			  AND active = 1
			ORDER BY sdvbasic_id
			""", nativeQuery = true)
	List<SalesDeliveryScheduleVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branch") Long branch);

//    boolean existsByDlvNoAndOrgIdAndBranch_Id(
//            String dlvNo,
//            Long orgId,
//            Long branchId);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getSalesDeliveryScheduleDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
			SELECT month_year
			FROM sdvbasic
			WHERE doc_id = ?1
			  AND branch = ?2
			  AND org_id = ?3
			  AND active = 1
			  AND cancel = 0
			""", nativeQuery = true)
	Set<Object[]> getMonthYearForSalesRejectionInv(String docId, Long branch, Long orgId);

	@Query(value = """
			SELECT
			    sv.dlv_no,
			    sv.dlv_date,
			    sv.month_of_schedule,
			    c.customer_id,
			    c.customer_name,
			    i.item_id,
			    i.item_description,
			    sd.actual_planned_qty AS plqty,
			    SUM(srd.despatch_qty) AS sqty,
			    (sd.actual_planned_qty - SUM(srd.despatch_qty)) AS pqty
			FROM sdvbasic sv
			INNER JOIN sdvdet sd
			    ON sd.sdvbasic_id = sv.sdvbasic_id
			INNER JOIN customer_header c
			    ON c.customer_id = sv.customer_id
			INNER JOIN sales_rejection_invoice_basic sr
			    ON sr.schedule_no = sv.doc_id
			INNER JOIN sales_rejection_invoice_detail srd
			    ON srd.sales_rejection_invoice_basic_id =
			       sr.sales_rejection_invoice_basic_id
			INNER JOIN item i
			    ON i.item_id = sd.item_id
			    AND i.item_id = srd.item
			WHERE sv.cancel = FALSE
			  AND sv.month_year = :mon
			  AND c.belongs_to = :under
			  AND c.customer_type = :party

			GROUP BY
			    sv.dlv_no,
			    sv.dlv_date,
			    sv.month_of_schedule,
			    c.customer_id,
			    c.customer_name,
			    sd.actual_planned_qty,
			    i.item_id,
			    i.item_description

			UNION

			SELECT
			    sv.dlv_no,
			    sv.dlv_date,
			    sv.month_of_schedule,
			    c.customer_id,
			    c.customer_name,
			    i.item_id,
			    i.item_description,
			    sd.actual_planned_qty AS plqty,
			    0 AS sqty,
			    sd.actual_planned_qty AS pqty
			FROM sdvbasic sv
			INNER JOIN sdvdet sd
			    ON sd.sdvbasic_id = sv.sdvbasic_id
			INNER JOIN customer_header c
			    ON c.customer_id = sv.customer_id
			INNER JOIN item i
			    ON i.item_id = sd.item_id
			WHERE sv.cancel = FALSE
			  AND sv.month_year = :mon
			  AND c.belongs_to = :under
			  AND c.customer_type = :party

			  AND NOT EXISTS (
			      SELECT 1
			      FROM sales_rejection_invoice_basic sr
			      INNER JOIN sales_rejection_invoice_detail srd
			          ON srd.sales_rejection_invoice_basic_id =
			             sr.sales_rejection_invoice_basic_id
			      WHERE sr.schedule_no = sv.doc_id
			        AND srd.item = i.item_id
			        AND sr.cancel = FALSE
			  )

			ORDER BY dlv_no
			""", nativeQuery = true)
	List<Object[]> getSalesDeliveryPendingReport(@Param("mon") String mon, @Param("under") Long under,
			@Param("party") String party);

//	monthly sch. Rev. details
	@Query(value = """
			SELECT
			    ob.modified_on AS modifiedDate,
			    ob.doc_date AS docDt,
			    i.item_id,
			    i.item_description,
			    c.customer_name AS partyName,
			    c.belongs_to AS belong,
			    od.order_qty AS oldValue
			FROM sdvbasic ob
			INNER JOIN sdvdet od
			    ON ob.sdvbasic_id = od.sdvbasic_id
			INNER JOIN item i
			    ON i.item_id = od.item_id
			INNER JOIN customer_header c
			    ON c.customer_id = ob.customer_id
			WHERE ob.cancel = FALSE
			  AND  (CAST(c.belongs_to AS CHAR) = :belongsTo
			  or
			  :belongsTo = 'ALL')
			  AND ob.month_year = :myear
			  AND ob.branch_id = :branch
			  AND ob.org_id = :orgId
			  AND ob.dlv_date BETWEEN :fromDate AND :toDate
			ORDER BY
			    c.belongs_to,
			    i.item_id
			""", nativeQuery = true)
	List<Object[]> getMonthlyScheduleRevDetails(@Param("belongsTo") String belongsTo, @Param("myear") String myear,
			@Param("branch") Long branch, @Param("orgId") Long orgId, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate);

//	delivery schedule - others
	@Query(value = """
			SELECT
			    i.item_id AS itemid,
			    i.item_code AS itemCode,
			    i.item_description AS itemdesc,
			    a.dlv_no AS ndivno,
			    a.dlv_date AS divdt,
			    p.customer_code AS customerCode,
			    p.customer_name AS customerName,

			    SUM(CASE WHEN DAY(s.delivery_date) = 1 THEN s.delivery_qty ELSE 0 END) AS d1,
			    SUM(CASE WHEN DAY(s.delivery_date) = 2 THEN s.delivery_qty ELSE 0 END) AS d2,
			    SUM(CASE WHEN DAY(s.delivery_date) = 3 THEN s.delivery_qty ELSE 0 END) AS d3,
			    SUM(CASE WHEN DAY(s.delivery_date) = 4 THEN s.delivery_qty ELSE 0 END) AS d4,
			    SUM(CASE WHEN DAY(s.delivery_date) = 5 THEN s.delivery_qty ELSE 0 END) AS d5,
			    SUM(CASE WHEN DAY(s.delivery_date) = 6 THEN s.delivery_qty ELSE 0 END) AS d6,
			    SUM(CASE WHEN DAY(s.delivery_date) = 7 THEN s.delivery_qty ELSE 0 END) AS d7,
			    SUM(CASE WHEN DAY(s.delivery_date) = 8 THEN s.delivery_qty ELSE 0 END) AS d8,
			    SUM(CASE WHEN DAY(s.delivery_date) = 9 THEN s.delivery_qty ELSE 0 END) AS d9,
			    SUM(CASE WHEN DAY(s.delivery_date) = 10 THEN s.delivery_qty ELSE 0 END) AS d10,
			    SUM(CASE WHEN DAY(s.delivery_date) = 11 THEN s.delivery_qty ELSE 0 END) AS d11,
			    SUM(CASE WHEN DAY(s.delivery_date) = 12 THEN s.delivery_qty ELSE 0 END) AS d12,
			    SUM(CASE WHEN DAY(s.delivery_date) = 13 THEN s.delivery_qty ELSE 0 END) AS d13,
			    SUM(CASE WHEN DAY(s.delivery_date) = 14 THEN s.delivery_qty ELSE 0 END) AS d14,
			    SUM(CASE WHEN DAY(s.delivery_date) = 15 THEN s.delivery_qty ELSE 0 END) AS d15,
			    SUM(CASE WHEN DAY(s.delivery_date) = 16 THEN s.delivery_qty ELSE 0 END) AS d16,
			    SUM(CASE WHEN DAY(s.delivery_date) = 17 THEN s.delivery_qty ELSE 0 END) AS d17,
			    SUM(CASE WHEN DAY(s.delivery_date) = 18 THEN s.delivery_qty ELSE 0 END) AS d18,
			    SUM(CASE WHEN DAY(s.delivery_date) = 19 THEN s.delivery_qty ELSE 0 END) AS d19,
			    SUM(CASE WHEN DAY(s.delivery_date) = 20 THEN s.delivery_qty ELSE 0 END) AS d20,
			    SUM(CASE WHEN DAY(s.delivery_date) = 21 THEN s.delivery_qty ELSE 0 END) AS d21,
			    SUM(CASE WHEN DAY(s.delivery_date) = 22 THEN s.delivery_qty ELSE 0 END) AS d22,
			    SUM(CASE WHEN DAY(s.delivery_date) = 23 THEN s.delivery_qty ELSE 0 END) AS d23,
			    SUM(CASE WHEN DAY(s.delivery_date) = 24 THEN s.delivery_qty ELSE 0 END) AS d24,
			    SUM(CASE WHEN DAY(s.delivery_date) = 25 THEN s.delivery_qty ELSE 0 END) AS d25,
			    SUM(CASE WHEN DAY(s.delivery_date) = 26 THEN s.delivery_qty ELSE 0 END) AS d26,
			    SUM(CASE WHEN DAY(s.delivery_date) = 27 THEN s.delivery_qty ELSE 0 END) AS d27,
			    SUM(CASE WHEN DAY(s.delivery_date) = 28 THEN s.delivery_qty ELSE 0 END) AS d28,
			    SUM(CASE WHEN DAY(s.delivery_date) = 29 THEN s.delivery_qty ELSE 0 END) AS d29,
			    SUM(CASE WHEN DAY(s.delivery_date) = 30 THEN s.delivery_qty ELSE 0 END) AS d30,
			    SUM(CASE WHEN DAY(s.delivery_date) = 31 THEN s.delivery_qty ELSE 0 END) AS d31

			FROM dlryschedule s

			INNER JOIN sdvdet sd
			    ON sd.sdvdet_id = s.sdvdet_id

			INNER JOIN sdvbasic a
			    ON a.sdvbasic_id = sd.sdvbasic_id

			INNER JOIN item i
			    ON i.item_id = sd.item_id

			INNER JOIN customer_header p
			    ON p.customer_id = a.customer_id

			WHERE p.customer_name NOT LIKE 'BOSCH%'
			  AND a.month_year = :monthYear
			  AND a.belongs_to = :belongsTo
			  AND i.item_code = :itemCode
			  AND a.branch_id = :branchId
			  AND a.org_id = :orgId
			  AND a.dlv_date BETWEEN :fromDate AND :toDate

			GROUP BY
			    i.item_id,
			    i.item_description,
			    a.dlv_no,
			    a.dlv_date,
			    p.customer_code,
			    p.customer_name

			ORDER BY
			    p.customer_code,
			    a.dlv_no,
			    i.item_id
			""", nativeQuery = true)
	List<Object[]> getDeliveryScheduleOthersReport(@Param("monthYear") String monthYear,
			@Param("belongsTo") Long belongsTo, @Param("itemCode") String itemCode, @Param("branchId") Long branchId,
			@Param("orgId") Long orgId, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

//	deliveryschedule-daywise

	@Query(value = """
			SELECT
			    i.item_id AS itemid,
			    i.item_description AS itemdesc,
			    a.dlv_no AS ndivno,
			    a.dlv_date AS divdt,
			    p.customer_code AS partyid,
			    p.customer_name AS partyname,

			    SUM(CASE WHEN DAY(s.delivery_date) = 1 THEN s.delivery_qty ELSE 0 END) AS d1,
			    SUM(CASE WHEN DAY(s.delivery_date) = 2 THEN s.delivery_qty ELSE 0 END) AS d2,
			    SUM(CASE WHEN DAY(s.delivery_date) = 3 THEN s.delivery_qty ELSE 0 END) AS d3,
			    SUM(CASE WHEN DAY(s.delivery_date) = 4 THEN s.delivery_qty ELSE 0 END) AS d4,
			    SUM(CASE WHEN DAY(s.delivery_date) = 5 THEN s.delivery_qty ELSE 0 END) AS d5,
			    SUM(CASE WHEN DAY(s.delivery_date) = 6 THEN s.delivery_qty ELSE 0 END) AS d6,
			    SUM(CASE WHEN DAY(s.delivery_date) = 7 THEN s.delivery_qty ELSE 0 END) AS d7,
			    SUM(CASE WHEN DAY(s.delivery_date) = 8 THEN s.delivery_qty ELSE 0 END) AS d8,
			    SUM(CASE WHEN DAY(s.delivery_date) = 9 THEN s.delivery_qty ELSE 0 END) AS d9,
			    SUM(CASE WHEN DAY(s.delivery_date) = 10 THEN s.delivery_qty ELSE 0 END) AS d10,
			    SUM(CASE WHEN DAY(s.delivery_date) = 11 THEN s.delivery_qty ELSE 0 END) AS d11,
			    SUM(CASE WHEN DAY(s.delivery_date) = 12 THEN s.delivery_qty ELSE 0 END) AS d12,
			    SUM(CASE WHEN DAY(s.delivery_date) = 13 THEN s.delivery_qty ELSE 0 END) AS d13,
			    SUM(CASE WHEN DAY(s.delivery_date) = 14 THEN s.delivery_qty ELSE 0 END) AS d14,
			    SUM(CASE WHEN DAY(s.delivery_date) = 15 THEN s.delivery_qty ELSE 0 END) AS d15,
			    SUM(CASE WHEN DAY(s.delivery_date) = 16 THEN s.delivery_qty ELSE 0 END) AS d16,
			    SUM(CASE WHEN DAY(s.delivery_date) = 17 THEN s.delivery_qty ELSE 0 END) AS d17,
			    SUM(CASE WHEN DAY(s.delivery_date) = 18 THEN s.delivery_qty ELSE 0 END) AS d18,
			    SUM(CASE WHEN DAY(s.delivery_date) = 19 THEN s.delivery_qty ELSE 0 END) AS d19,
			    SUM(CASE WHEN DAY(s.delivery_date) = 20 THEN s.delivery_qty ELSE 0 END) AS d20,
			    SUM(CASE WHEN DAY(s.delivery_date) = 21 THEN s.delivery_qty ELSE 0 END) AS d21,
			    SUM(CASE WHEN DAY(s.delivery_date) = 22 THEN s.delivery_qty ELSE 0 END) AS d22,
			    SUM(CASE WHEN DAY(s.delivery_date) = 23 THEN s.delivery_qty ELSE 0 END) AS d23,
			    SUM(CASE WHEN DAY(s.delivery_date) = 24 THEN s.delivery_qty ELSE 0 END) AS d24,
			    SUM(CASE WHEN DAY(s.delivery_date) = 25 THEN s.delivery_qty ELSE 0 END) AS d25,
			    SUM(CASE WHEN DAY(s.delivery_date) = 26 THEN s.delivery_qty ELSE 0 END) AS d26,
			    SUM(CASE WHEN DAY(s.delivery_date) = 27 THEN s.delivery_qty ELSE 0 END) AS d27,
			    SUM(CASE WHEN DAY(s.delivery_date) = 28 THEN s.delivery_qty ELSE 0 END) AS d28,
			    SUM(CASE WHEN DAY(s.delivery_date) = 29 THEN s.delivery_qty ELSE 0 END) AS d29,
			    SUM(CASE WHEN DAY(s.delivery_date) = 30 THEN s.delivery_qty ELSE 0 END) AS d30,
			    SUM(CASE WHEN DAY(s.delivery_date) = 31 THEN s.delivery_qty ELSE 0 END) AS d31

			FROM dlryschedule s

			INNER JOIN sdvdet sd
			    ON sd.sdvdet_id = s.sdvdet_id

			INNER JOIN sdvbasic a
			    ON a.sdvbasic_id = sd.sdvbasic_id

			INNER JOIN item i
			    ON i.item_id = sd.item_id

			INNER JOIN customer_header p
			    ON p.customer_id = a.customer_id

			WHERE a.month_year = :monthYear
			  AND a.dlv_date BETWEEN :fromDate AND :toDate
			  AND a.branch_id = :branchId
			  AND a.org_id = :orgId

			GROUP BY
			    i.item_id,
			    i.item_description,
			    a.dlv_no,
			    a.dlv_date,
			    p.customer_code,
			    p.customer_name

			ORDER BY a.dlv_no
			""", nativeQuery = true)
	List<Object[]> getDeliveryScheduleDayWiseReport(@Param("monthYear") String monthYear,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate, @Param("branchId") Long branchId,
			@Param("orgId") Long orgId);

//	schedule vs sale

	@Query(value = """
			SELECT
			    partyid,
			    partyname,
			    itemid,
			    itemdesc,
			    cpartno,
			    pLqty,
			    sqty,
			    PQTY,
			    (pLqty * RATE) AS shvalue,
			    (sqty * RATE) AS sales,
			    RATE
			FROM (
			    SELECT
			        partyid,
			        partyname,
			        itemid,
			        itemdesc,
			        cpartno,
			        SUM(pLqty) AS pLqty,
			        SUM(sqty) AS sqty,
			        SUM(PQTY) AS PQTY,
			        RATE
			    FROM (
			        SELECT
			            ob.doc_id AS docid,
			            ob.doc_date AS docdt,
			            a.dlv_no AS divno,
			            a.dlv_date AS divdt,
			            a.month_of_schedule AS month,
			            c.customer_code AS partyid,
			            c.customer_name AS partyname,
			            i.item_id AS itemid,
			            i.item_description AS itemdesc,
			            i.customer_part_no AS cpartno,
			            SUM(b.actual_planned_qty) AS pLqty,
			            SUM(dd.despatch_qty) AS sqty,
			            SUM(b.actual_planned_qty) - SUM(dd.despatch_qty) AS PQTY,
			            od.order_rate AS RATE
			        FROM sdvbasic a
			        INNER JOIN sdvdet b
			            ON a.sdvbasic_id = b.sdvbasic_id
			        INNER JOIN customer_header c
			            ON a.customer_id = c.customer_id
			        INNER JOIN order_acceptance_basic ob
			            ON ob.doc_id = b.so_no_contractno
			        INNER JOIN order_acceptance_detail od
			            ON ob.order_acceptance_basic_id = od.order_acceptance_basic_id
			        INNER JOIN item i
			            ON b.item_id = i.item_id
			           AND od.item = i.item_id
			        INNER JOIN sales_rejection_invoice_detail dd
			            ON dd.item = i.item_id
			        INNER JOIN sales_rejection_invoice_basic dc
			            ON dc.sales_rejection_invoice_basic_id =
			               dd.sales_rejection_invoice_basic_id
			        WHERE a.cancel = FALSE
			          AND dc.cancel = FALSE
			          AND a.month_year = :mon
			          AND c.belongs_to = :under
			          AND a.branch_id = :branch
			          AND a.org_id = :orgId
			        GROUP BY
			            a.dlv_no,
			            a.dlv_date,
			            a.month_of_schedule,
			            c.customer_code,
			            c.customer_name,
			            i.item_id,
			            i.item_description,
			            i.customer_part_no,
			            ob.doc_id,
			            ob.doc_date,
			            od.order_rate

			        UNION

			        SELECT
			            sc.doc_id AS docid,
			            sc.doc_date AS docdt,
			            a.dlv_no AS divno,
			            a.dlv_date AS divdt,
			            a.month_of_schedule AS month,
			            c.customer_code AS partyid,
			            c.customer_name AS partyname,
			            i.item_id AS itemid,
			            i.item_description AS itemdesc,
			            i.customer_part_no AS cpartno,
			            MAX(b.actual_planned_qty) AS pLqty,
			            SUM(dd.despatch_qty) AS sqty,
			            MAX(b.actual_planned_qty) - SUM(dd.despatch_qty) AS PQTY,
			            d.order_rate AS RATE
			        FROM sdvbasic a
			        INNER JOIN sdvdet b
			            ON a.sdvbasic_id = b.sdvbasic_id
			        INNER JOIN customer_header c
			            ON a.customer_id = c.customer_id
			        INNER JOIN sales_contract_basic sc
			            ON sc.customer_contract_no = b.so_no_contractno
			        INNER JOIN sales_contract_detail d
			            ON d.salescontract_id = sc.salescontract_id
			        INNER JOIN item i
			            ON b.item_id = i.item_id
			           AND d.item = i.item_id
			        INNER JOIN sales_rejection_invoice_detail dd
			            ON dd.item = i.item_id
			        INNER JOIN sales_rejection_invoice_basic dc
			            ON dc.sales_rejection_invoice_basic_id =
			               dd.sales_rejection_invoice_basic_id
			        WHERE a.cancel = FALSE
			          AND dc.cancel = FALSE
			          AND a.month_year = :mon
			          AND c.belongs_to = :under
			          AND a.branch_id = :branch
			          AND a.org_id = :orgId
			        GROUP BY
			            sc.doc_id,
			            sc.doc_date,
			            a.dlv_no,
			            a.dlv_date,
			            a.month_of_schedule,
			            c.customer_code,
			            c.customer_name,
			            i.item_id,
			            i.item_description,
			            i.customer_part_no,
			            d.order_rate

			        UNION

			        SELECT
			            sc.doc_id AS docid,
			            sc.doc_date AS docdt,
			            a.dlv_no AS divno,
			            a.dlv_date AS divdt,
			            a.month_of_schedule AS month,
			            c.customer_code AS partyid,
			            c.customer_name AS partyname,
			            i.item_id AS itemid,
			            i.item_description AS itemdesc,
			            i.customer_part_no AS cpartno,
			            b.actual_planned_qty AS pLqty,
			            0 AS sqty,
			            b.actual_planned_qty AS PQTY,
			            d.order_rate AS RATE
			        FROM sdvbasic a
			        INNER JOIN sdvdet b
			            ON a.sdvbasic_id = b.sdvbasic_id
			        INNER JOIN customer_header c
			            ON a.customer_id = c.customer_id
			        INNER JOIN item i
			            ON b.item_id = i.item_id
			        INNER JOIN sales_contract_basic sc
			            ON sc.salescontract_id = b.so_no_contractno
			        INNER JOIN sales_contract_detail d
			            ON d.salescontract_id = sc.salescontract_id
			           AND d.item = b.item_id
			        WHERE a.cancel = FALSE
			          AND a.month_year = :mon
			          AND c.belongs_to = :under
			          AND a.branch_id = :branch
			          AND a.org_id = :orgId
			          AND NOT EXISTS (
			              SELECT 1
			              FROM sales_rejection_invoice_basic dc
			              INNER JOIN sales_rejection_invoice_detail dd
			                  ON dc.sales_rejection_invoice_basic_id =
			                     dd.sales_rejection_invoice_basic_id
			              WHERE dc.cancel = FALSE
			                AND dd.item = i.item_id
			          )

			        UNION

			        SELECT
			            ob.doc_id AS docid,
			            ob.doc_date AS docdt,
			            a.dlv_no AS divno,
			            a.dlv_date AS divdt,
			            a.month_of_schedule AS month,
			            c.customer_code AS partyid,
			            c.customer_name AS partyname,
			            i.item_id AS itemid,
			            i.item_description AS itemdesc,
			            i.customer_part_no AS cpartno,
			            b.actual_planned_qty AS pLqty,
			            0 AS sqty,
			            b.actual_planned_qty AS PQTY,
			            od.order_rate AS RATE
			        FROM sdvbasic a
			        INNER JOIN sdvdet b
			            ON a.sdvbasic_id = b.sdvbasic_id
			        INNER JOIN customer_header c
			            ON a.customer_id = c.customer_id
			        INNER JOIN item i
			            ON b.item_id = i.item_id
			        INNER JOIN order_acceptance_basic ob
			            ON ob.doc_id = b.so_no_contractno
			        INNER JOIN order_acceptance_detail od
			            ON od.order_acceptance_basic_id =
			               ob.order_acceptance_basic_id
			           AND od.item = b.item_id
			        WHERE a.cancel = FALSE
			          AND a.month_year = :mon
			          AND c.belongs_to = :under
			          AND a.branch_id = :branch
			          AND a.org_id = :orgId
			          AND NOT EXISTS (
			              SELECT 1
			              FROM sales_rejection_invoice_basic dc
			              INNER JOIN sales_rejection_invoice_detail dd
			                  ON dc.sales_rejection_invoice_basic_id =
			                     dd.sales_rejection_invoice_basic_id
			              WHERE dc.cancel = FALSE
			                AND dd.item = i.item_id
			          )
			    ) AS union_data
			    GROUP BY
			        partyid,
			        partyname,
			        itemid,
			        itemdesc,
			        cpartno,
			        RATE
			) AS final_data
			ORDER BY 1
			""", nativeQuery = true)
	List<Object[]> getScheduleVsSalesReport(@Param("mon") String mon, @Param("under") Long under,
			@Param("branch") Long branch, @Param("orgId") Long orgId);
	
//	delivery schedule year wise
	
	@Query(value = """
	        SELECT
	            i.item_code AS ITEMID,
	            i.item_description AS ITEMDESCRIPTION,
	            SUM(CASE
	                WHEN UPPER(a.month_of_schedule) IN ('JANUARY', 'JAN')
	                THEN b.actual_planned_qty ELSE 0
	            END) AS JAN,
	            SUM(CASE
	                WHEN UPPER(a.month_of_schedule) IN ('FEBRUARY', 'FEB')
	                THEN b.actual_planned_qty ELSE 0
	            END) AS FEB,
	            SUM(CASE
	                WHEN UPPER(a.month_of_schedule) IN ('MARCH', 'MAR')
	                THEN b.actual_planned_qty ELSE 0
	            END) AS MAR,
	            SUM(CASE
	                WHEN UPPER(a.month_of_schedule) IN ('APRIL', 'APR')
	                THEN b.actual_planned_qty ELSE 0
	            END) AS APR,
	            SUM(CASE
	                WHEN UPPER(a.month_of_schedule) IN ('MAY')
	                THEN b.actual_planned_qty ELSE 0
	            END) AS MAY,
	            SUM(CASE
	                WHEN UPPER(a.month_of_schedule) IN ('JUNE', 'JUN')
	                THEN b.actual_planned_qty ELSE 0
	            END) AS JUN,
	            SUM(CASE
	                WHEN UPPER(a.month_of_schedule) IN ('JULY', 'JUL')
	                THEN b.actual_planned_qty ELSE 0
	            END) AS JUL,
	            SUM(CASE
	                WHEN UPPER(a.month_of_schedule) IN ('AUGUST', 'AUG')
	                THEN b.actual_planned_qty ELSE 0
	            END) AS AUG,
	            SUM(CASE
	                WHEN UPPER(a.month_of_schedule) IN ('SEPTEMBER', 'SEP')
	                THEN b.actual_planned_qty ELSE 0
	            END) AS SEP,
	            SUM(CASE
	                WHEN UPPER(a.month_of_schedule) IN ('OCTOBER', 'OCT')
	                THEN b.actual_planned_qty ELSE 0
	            END) AS OCT,
	            SUM(CASE
	                WHEN UPPER(a.month_of_schedule) IN ('NOVEMBER', 'NOV')
	                THEN b.actual_planned_qty ELSE 0
	            END) AS NOV,
	            SUM(CASE
	                WHEN UPPER(a.month_of_schedule) IN ('DECEMBER', 'DEC')
	                THEN b.actual_planned_qty ELSE 0
	            END) AS DECE
	        FROM sdvbasic a
	        INNER JOIN sdvdet b
	            ON a.sdvbasic_id = b.sdvbasic_id
	        INNER JOIN item i
	            ON b.item_id = i.item_id
	        WHERE a.cancel = FALSE
	          AND a.month_year = :year
	          AND a.belongs_to = :belongs
	          AND a.branch_id = :branch
	          AND a.org_id = :orgId
	        GROUP BY
	            i.item_code,
	            i.item_description
	        ORDER BY
	            i.item_code
	        """, nativeQuery = true)
	List<Object[]> getDeliveryScheduleYearWiseReport(
	        @Param("year") String year,
	        @Param("belongs") String belongs,
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId
	);
}