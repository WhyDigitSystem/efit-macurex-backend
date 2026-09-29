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

}