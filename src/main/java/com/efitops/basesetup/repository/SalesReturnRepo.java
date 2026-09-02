package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.SalesReturnVO;

public interface SalesReturnRepo extends JpaRepository<SalesReturnVO, Long> {

	@Query(value = """
			SELECT *
			FROM sales_return_basic
			WHERE org_id = :orgId
			  AND branch_id = :branchId
			  AND cancel = false
			  AND active = true
			ORDER BY sales_return_basicid DESC
			""", nativeQuery = true)
	List<SalesReturnVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branchId") Long branchId);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getSalesReturnDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
			SELECT
			    doc_id,
			    doc_date,
			    doc_type
			FROM sales_rejection_invoice_basic
			WHERE org_id = :orgId
			  AND branch = :branch
			  AND doc_type IN ('Other Sales Invoice', 'Invoice')
			ORDER BY doc_id
			""", nativeQuery = true)
	List<Object[]> getSalesRejectionInvoiceforSalesReturn(@Param("orgId") Long orgId, @Param("branch") Long branch);
//    boolean existsByDocNoAndOrgIdAndBranch_Id(
//            String docNo,
//            Long orgId,
//            Long branchId);

	@Query(value = """
				            SELECT
				                g.gate_inward_entry_basic_id AS dateInwardId,
				                g.doc_id AS gateInwardDocId,
				                g.doc_date AS docDate,
				                g.supplier_invoice_number AS supplierInvoiceNumber,
				                g.supplier_invoice_date AS supplierInvoiceDate
				            FROM gate_inward_entry_basic g
				            INNER JOIN customer_header c
				                ON c.customer_id = g.customer
				            INNER JOIN sales_rejection_invoice_basic sri
				                ON sri.doc_id = :invno
				                AND sri.doc_type IN ('Other Sales Invoice', 'Invoice')
				                AND sri.cancel = 0
				            WHERE g.cancel = 0
				              AND g.active = 1
				              AND c.customer_id = :custcode
							AND UPPER(:type) = 'WITH OUR INVOICE'
				              AND g.org_id = :orgId
				              AND g.branch = :branch
				              AND NOT EXISTS (
				                  SELECT 1
				                  FROM sales_return_basic x
				                  WHERE x.cancel = 0
				                    AND x.gate_pass_no = g.doc_id
				              )

				            UNION

				            SELECT
				                g.gate_inward_entry_basic_id AS dateInwardId,
				                g.doc_id AS gateInwardDocId,
				                g.doc_date AS docDate,
				                g.supplier_invoice_number AS supplierInvoiceNumber,
				                g.supplier_invoice_date AS supplierInvoiceDate
				            FROM gate_inward_entry_basic g
				            INNER JOIN customer_header c
				                ON c.customer_id = g.customer
				            WHERE g.cancel = 0
				              AND g.active = 1
				              AND c.customer_id = :custcode
				              AND UPPER(:type) = 'WITHOUT OUR INVOICE'
				              AND g.org_id = :orgId
				              AND g.branch = :branch
				              AND NOT EXISTS (
				                  SELECT 1
				                  FROM sales_return_basic x
				                  WHERE x.cancel = 0
				                    AND x.gate_pass_no = g.doc_id
				              )

				            ORDER BY docDate DESC
				            """, nativeQuery = true)
	List<Object[]> getGateInwardForSalesReturn(@Param("custcode") Long custcode, @Param("type") String type,
			@Param("invno") String invno, @Param("orgId") Long orgId, @Param("branch") Long branch);

	@Query(value = """
	        SELECT
	            im.item_id AS itemId,
	            im.item_code AS itemCode,
	            im.item_description AS itemDescription,
	            h.hsn AS hsnSacCode,
	            d.despatch_qty AS qtySold,
	            u.unitmaster_id AS unitId,
	            u.unit_id AS unitCode,
	            u.description AS unitDescription,
	            gr.rate AS rate,
	            gr.cgst AS cgstRate,
	            gr.sgst AS sgstRate,
	            gr.igst AS igstRate,
	            d.new_rate AS newRate
	        FROM sales_rejection_invoice_detail d
	        INNER JOIN sales_rejection_invoice_basic sri
	            ON sri.sales_rejection_invoice_basic_id =
	               d.sales_rejection_invoice_basic_id
	        INNER JOIN item im
	            ON im.item_id = d.item
	        LEFT JOIN hsn h
	            ON h.hsn_id = im.hsn_code
	        LEFT JOIN unitmaster u
	            ON u.unitmaster_id = d.unit
	        LEFT JOIN gstratemaster gr
	            ON gr.hsn_sac_code = h.hsn_id
	            AND gr.active = 1
	            AND gr.cancel = 0
	        WHERE sri.doc_id = :invoiceId
	          AND sri.org_id = :orgId
	          AND sri.branch = :branch
	          AND sri.active = 1
	          AND sri.cancel = 0
	        ORDER BY im.item_code
	        """, nativeQuery = true)
	Set<Object[]> getSalesRejectionInvoiceItemDetailsForSalesRetuen(
	        @Param("invoiceId") String invoiceId,
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);

	@Query(value = """
	        SELECT
	            im.item_id As itemId,
	            im.item_code AS itemCode,
	            im.item_description AS itemDescription,
	            h.hsn AS hsnSacCode,

	            u.unitmaster_id AS unitId,
	            u.unit_id AS unitCode,
	            u.description AS unitDescription,

	            gr.rate AS rate,
	            gr.cgst AS cgstRate,
	            gr.sgst AS sgstRate,
	            gr.igst AS igstRate

	        FROM item im

	        LEFT JOIN hsn h
	            ON h.hsn_id = im.hsn_code

	        LEFT JOIN unitmaster u
	            ON u.unitmaster_id = im.purchase_unit

	        LEFT JOIN gstratemaster gr
	            ON gr.hsn_sac_code = h.hsn_id
	            AND gr.active = 1
	            AND gr.cancel = 0

	        WHERE im.cancel = 0
	          AND im.active = 1
	          AND im.org_id = :orgId
	          AND im.branch = :branch

	        ORDER BY im.item_code
	        """, nativeQuery = true)
	Set<Object[]> getItemDetailsForSalesReturn(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);

	
}