package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesRejectionInvoiceVO;

@Repository
public interface SalesRejectionInvoiceRepo extends JpaRepository<SalesRejectionInvoiceVO, Long> {

	@Query(value = "SELECT * " + "FROM sales_rejection_invoice_basic " + "WHERE org_id = :orgId "
			+ "AND branch = :branch " + "AND active = 1 " + "AND cancel = 0 "
			+ "ORDER BY sales_rejection_invoice_basic_id DESC", nativeQuery = true)
	List<SalesRejectionInvoiceVO> getSalesRejectionInvoiceByOrgId(@Param("orgId") Long orgId,
			@Param("branch") Long branch);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getSalesRejectionInvoiceDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
			SELECT
			    sr.doc_id,
			    sr.doc_date,
			    sd.despatch_qty,
			    c.customer_id,
			    c.customer_code,
			    c.customer_name,
			    i.item_code,
			    i.item_description,
			    o.doc_id AS order_acceptance_no,
			    u.unitmaster_id,
			    sr.purchase_order,
			    sr.purchase_order_date,
			    c.city,
			    sr.net_amount,
			    sr.total_ass_val
			FROM sales_rejection_invoice_basic sr

			INNER JOIN sales_rejection_invoice_detail sd
			    ON sd.sales_rejection_invoice_basic_id =
			       sr.sales_rejection_invoice_basic_id

			INNER JOIN order_acceptance_basic o
			    ON o.customer_purchase_order_no =
			       sr.purchase_order

			INNER JOIN order_acceptance_detail od
			    ON od.order_acceptance_basic_id =
			       o.order_acceptance_basic_id
			   AND od.item = sd.item

			INNER JOIN customer_header c
			    ON c.customer_id = sr.customer

			INNER JOIN item i
			    ON i.item_id = sd.item

			INNER JOIN unitmaster u
			    ON u.unitmaster_id = i.primary_unit

			WHERE c.customer_name = :customerName
			  AND i.item_code = :itemCode
			   AND sr.doc_type = 'INVOICE'
			  AND sr.doc_date BETWEEN :fromDate AND :toDate
			  AND sr.branch = :branch
			  AND sr.org_id = :orgId
			""", nativeQuery = true)
	List<Object[]> getSalesRegisterProductWiseReport(@Param("customerName") String customerName,
			@Param("itemCode") String itemCode, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("branch") Long branch, @Param("orgId") Long orgId);

//	Sales register location wise

	@Query(value = """
			     SELECT
			         sr.doc_id,
			         sr.doc_date,
			         sd.despatch_qty,
			         c.customer_id,
			         c.customer_code,
			         c.customer_name,
			         i.item_code,
			         i.item_description,
			         o.doc_id AS order_acceptance_no,
			         u.unit_id,
			         cc.city,
			         sr.total_ass_val,
			         sr.net_amount,
			         CASE
			    WHEN sd.new_rate = 0
			        THEN sd.rate_in_selected_currency
			    ELSE sd.new_rate
			END AS rate
			     FROM sales_rejection_invoice_basic sr

			     INNER JOIN sales_rejection_invoice_detail sd
			         ON sd.sales_rejection_invoice_basic_id =
			            sr.sales_rejection_invoice_basic_id

			     INNER JOIN order_acceptance_basic o
			         ON o.customer_purchase_order_no =
			            sr.purchase_order

			     INNER JOIN order_acceptance_detail od
			         ON od.order_acceptance_basic_id =
			            o.order_acceptance_basic_id
			        AND od.item = sd.item

			     INNER JOIN customer_header c
			         ON c.customer_id = sr.customer

			     INNER JOIN item i
			         ON i.item_id = sd.item

			     INNER JOIN unitmaster u
			         ON u.unitmaster_id = i.primary_unit
			    inner join city cc on cc.city_id = c.city


			     WHERE (cc.city = :location OR :location = 'ALL')
			       AND i.item_code = :itemCode
			       AND sr.doc_type = 'INVOICE'
			       AND sr.doc_date BETWEEN :fromDate AND :toDate
			       AND sr.branch = :branch
			       AND sr.org_id = :orgId
			     """, nativeQuery = true)
	List<Object[]> getSalesRegisterLocationWiseReport(@Param("location") String location,
			@Param("itemCode") String itemCode, @Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("branch") Long branch, @Param("orgId") Long orgId);

//	sales customer/part no -c

	@Query(value = """
			SELECT
			    sr.doc_id,
			    sr.doc_date,
			    c.customer_code,
			    c.customer_name,
			    i.item_code,
			    i.item_description,
			    sd.customer_part_no,
			    u.unit_id,
			    sd.despatch_qty,
			    sr.total_ass_val,
			    SUM(sd.igst_amount) AS igst,
			    SUM(sd.cgst_amount) AS cgst,
			    SUM(sd.sgst_amount) AS sgst
			FROM sales_rejection_invoice_basic sr
			INNER JOIN sales_rejection_invoice_detail sd
			    ON sd.sales_rejection_invoice_basic_id =
			       sr.sales_rejection_invoice_basic_id
			INNER JOIN customer_header c
			    ON c.customer_id = sr.customer
			INNER JOIN item i
			    ON i.item_id = sd.item
			INNER JOIN unitmaster u
			    ON u.unitmaster_id = sd.unit
			WHERE sr.branch = :branch
			ANd sr.org_id = :orgId
			  AND sr.doc_date BETWEEN :fromDate AND :toDate
			  AND c.customer_name = :customerName
			  AND (sr.doc_type IS NULL OR sr.doc_type = 'INVOICE')
			GROUP BY
			    sr.doc_id,
			    sr.doc_date,
			    c.customer_code,
			    c.customer_name,
			    i.item_code,
			    i.item_description,
			    sd.customer_part_no,
			    u.unit_id,
			    sd.despatch_qty,
			    sr.total_ass_val
			""", nativeQuery = true)
	List<Object[]> getSalesCustomerPartNoCumlReport(@Param("branch") Long branch, Long orgId,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("customerName") String customerName);

//	sales register customerwise

	@Query(value = """
			SELECT
			    srb.doc_id,
			    srb.doc_date,
			    srb.cancel,
			    srb.cancel_remarks,
			    srb.purchase_order,
			    c.customer_name,
			    c.customer_code,
			    i.item_code,
			    i.item_description,
			    srd.customer_part_no,
			    u.unit_id,

			    CASE
			        WHEN srb.cancel = FALSE
			        THEN srd.despatch_qty
			        ELSE 0
			    END AS qty,

			    CASE
			        WHEN srd.new_rate = 0
			        THEN srd.rate_in_selected_currency
			        ELSE srd.new_rate
			    END AS rate,

			    CASE
			        WHEN srb.cancel = FALSE
			        THEN srb.total_ass_val
			        ELSE 0
			    END AS amtInRs,

			    CASE
			        WHEN srb.cancel = FALSE
			        THEN srb.net_amount
			        ELSE 0
			    END AS vatVal,

			    SUM(srd.sgst_amount) AS sgst,
			    SUM(srd.cgst_amount) AS cgst,
			    SUM(srd.igst_amount) AS igst

			FROM sales_rejection_invoice_basic srb

			INNER JOIN customer_header c
			    ON c.customer_id = srb.customer

			INNER JOIN sales_rejection_invoice_detail srd
			    ON srd.sales_rejection_invoice_basic_id =
			       srb.sales_rejection_invoice_basic_id

			INNER JOIN item i
			    ON i.item_id = srd.item

			INNER JOIN unitmaster u
			    ON u.unitmaster_id = i.primary_unit

			WHERE srb.belongs_to = :belongsTo
			  AND srb.doc_date BETWEEN :fromDate AND :toDate
			  AND c.customer_name = :customerName
			  AND srb.branch = :branch
			  AND srb.org_id = :orgId

			GROUP BY
			    srb.doc_id,
			    srb.doc_date,
			    srb.cancel,
			    srb.cancel_remarks,
			    srb.purchase_order,
			    c.customer_name,
			    c.customer_code,
			    i.item_code,
			    i.item_description,
			    srd.customer_part_no,
			    u.unit_id,
			    srb.total_ass_val,
			    srb.net_amount,
			    srd.despatch_qty,
			    srd.new_rate,
			    srd.rate_in_selected_currency
			""", nativeQuery = true)
	List<Object[]> getSalesRegisterCustomerWiseReport(@Param("belongsTo") Long belongsTo,
			@Param("fromDate") String fromDate, @Param("toDate") String toDate,
			@Param("customerName") String customerName, @Param("branch") Long branch, @Param("orgId") Long orgId);

//	Regular Lc Sales Report

	@Query(value = """
			SELECT
			    sri.doc_id AS docid,
			    sri.doc_date AS docdt,
			    sri.cancel,
			    sri.cancel_remarks,
			    c.customer_code AS partyid,
			    c.customer_name AS partyname,
			    sri.purchase_order AS pono,
			    sri.purchase_order_date AS podt,
			    i.item_id AS itemid,
			    i.item_description AS itemdesc,
			    srid.customer_part_no AS cpart,
			    u.unitmaster_id AS unitid,

			    CASE
			        WHEN sri.cancel = FALSE THEN srid.despatch_qty
			        ELSE 0
			    END AS qty,

			    srid.new_rate AS rate,

			    CASE
			        WHEN sri.cancel = FALSE THEN srid.amount_in_rs
			        ELSE 0
			    END AS amount,

			    SUM(COALESCE(srid.igst_amount, 0)) AS igst,
			    SUM(COALESCE(srid.sgst_amount, 0)) AS sgst,
			    SUM(COALESCE(srid.cgst_amount, 0)) AS cgst,
			    MAX(COALESCE(sri.tcs_amount, 0)) AS tcs,

			    CASE
			        WHEN sri.cancel = FALSE THEN sri.net_amount
			        ELSE 0
			    END AS grossamt

			FROM sales_rejection_invoice_basic sri

			INNER JOIN sales_rejection_invoice_detail srid
			    ON srid.sales_rejection_invoice_basic_id =
			       sri.sales_rejection_invoice_basic_id

			INNER JOIN customer_header c
			    ON c.customer_id = sri.customer

			INNER JOIN item i
			    ON i.item_id = srid.item

			LEFT JOIN unitmaster u
			    ON u.unitmaster_id = srid.unit

			WHERE sri.belongs_to = :belongsTo
			  AND sri.branch = :branch
			  AND sri.org_id = :orgId
			  AND sri.doc_date BETWEEN :fromDate AND :toDate

			GROUP BY
			    sri.doc_id,
			    sri.doc_date,
			    sri.cancel,
			    sri.cancel_remarks,
			    c.customer_code,
			    c.customer_name,
			    sri.purchase_order,
			    sri.purchase_order_date,
			    i.item_id,
			    i.item_description,
			    srid.customer_part_no,
			    u.unitmaster_id,
			    srid.despatch_qty,
			    srid.new_rate,
			    srid.amount_in_rs,
			    sri.net_amount

			ORDER BY sri.doc_id
			""", nativeQuery = true)
	List<Object[]> getRegularLCSalesReport(@Param("belongsTo") Long belongsTo, @Param("branch") Long branch,
			@Param("orgId") Long orgId, @Param("fromDate") String fromDate, @Param("toDate") String toDate);


}
