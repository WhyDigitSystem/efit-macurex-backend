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

}