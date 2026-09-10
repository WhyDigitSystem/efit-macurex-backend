// PurchaseBillRepo.java
package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseBillVO;

@Repository
public interface PurchaseBillRepo extends JpaRepository<PurchaseBillVO, Long> {

	@Query(nativeQuery = true, value = "select * from purchase_bill_basic where purchase_bill_basic_id=?1")
	PurchaseBillVO getPurchaseBillById(Long id);

	@Query(nativeQuery = true, value = "select * from purchase_bill_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<PurchaseBillVO> getPurchaseBillByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,'0')) AS docid "
			+ "from documenttypemapping_details where org_id=?1 and screen_code=?2")
	String getPurchaseBillDocId(Long orgId, String screenCode);

	@Query(value = """
			            SELECT
			    c.customer_id,
			    c.customer_code,
			    c.customer_name,
			    c.ecc_type,
			    c.is_gst_applicable,
			    c.gst_no,
			    c.gst_type,
			    c.gst_state AS gst_state_id,
			    g.state_code,
			    g.state_name,
			    c.is_registered
			FROM customer_header c

			LEFT JOIN gststatemaster g
			    ON c.gst_state = g.gststatemaster_id

			LEFT JOIN listofvaluesdetails a
			    ON c.customer_category = a.listofvaluesdetails_id

			LEFT JOIN listofvaluesdetails b
			    ON c.customer_category1 = b.listofvaluesdetails_id

			LEFT JOIN listofvaluesdetails cc
			    ON c.customer_category2 = cc.listofvaluesdetails_id

			WHERE c.cancel = FALSE
			  AND c.active = TRUE
			  AND c.branch = :branch
			  AND c.org_id = :orgId
			  AND (
			        UPPER(a.value_description) = 'SUPPLIER'
			        OR UPPER(b.value_description) = 'SUPPLIER'
			        OR UPPER(cc.value_description) = 'SUPPLIER'
			      )

			ORDER BY c.customer_code
			            """, nativeQuery = true)
	List<Object[]> getSuppliersForPurchaseBill(@Param("orgId") Long orgId, @Param("branch") Long branch);

	// grn no number dropdown for purchase bill

	@Query(value = """
	        SELECT
	            gb.grn_basic_id,
	            gb.doc_id AS grn_no,
	            gb.doc_date AS grn_date,
	            gb.currency,
	            cm.currency AS currency_name,
	            cm.currency_description,
	            gb.exchange_rate,
	            gb.po_no AS po_no,
	            gb.party_dc_no AS vendor_dc_no,
	            gb.doc_date AS vendor_dc_date,
	            pcb.purchase_order_type AS po_type,
	            gb.modvat_copy_received AS modvat,
	            gb.party_dc_no AS supplier_dc_inv_no,
	            STR_TO_DATE(
	                gb.supplier_dc_date,
	                '%Y-%m-%d'
	            ) AS supplier_dc_inv_date

	        FROM grn_basic gb

	        LEFT JOIN purchase_contract_basic pcb
	            ON pcb.doc_id = gb.po_no
	            AND pcb.org_id = gb.org_id
	            AND pcb.branch = gb.branch

	        LEFT JOIN currency cm
	            ON cm.currency_id = gb.currency
	            AND cm.active = TRUE
	            AND cm.cancel = FALSE

	        WHERE gb.org_id = :orgId
	          AND gb.branch = :branch
	          AND gb.supplier_code = :supplier

	          AND gb.active = TRUE
	          AND gb.cancel = FALSE

	          AND gb.po_no IS NOT NULL
	          AND gb.po_no <> ''

	          AND NOT EXISTS (
	              SELECT 1
	              FROM purchase_bill_basic pbb
	              WHERE pbb.grn_no = gb.doc_id
	                AND pbb.org_id = gb.org_id
	                AND pbb.branch = gb.branch
	                AND pbb.active = TRUE
	                AND pbb.cancel = FALSE
	          )

	        UNION

	        SELECT
	            gb.grn_basic_id,
	            gb.doc_id AS grn_no,
	            gb.doc_date AS grn_date,
	            gb.currency,
	            cm.currency AS currency_name,
	            cm.currency_description,
	            gb.exchange_rate,
	            pcb.doc_id AS po_no,
	            gb.party_dc_no AS vendor_dc_no,
	            gb.doc_date AS vendor_dc_date,
	            pcb.purchase_order_type AS po_type,
	            gb.modvat_copy_received AS modvat,
	            gb.party_dc_no AS supplier_dc_inv_no,
	            STR_TO_DATE(
	                gb.supplier_dc_date,
	                '%Y-%m-%d'
	            ) AS supplier_dc_inv_date

	        FROM grn_basic gb

	        INNER JOIN purchase_contract_basic pcb
	            ON pcb.supplier = gb.supplier_code
	            AND pcb.org_id = gb.org_id
	            AND pcb.branch = gb.branch
	            AND pcb.currency = gb.currency

	        LEFT JOIN currency cm
	            ON cm.currency_id = gb.currency
	            AND cm.active = TRUE
	            AND cm.cancel = FALSE

	        WHERE gb.org_id = :orgId
	          AND gb.branch = :branch
	          AND gb.supplier_code = :supplier

	          AND gb.active = TRUE
	          AND gb.cancel = FALSE

	          AND (
	              gb.po_no IS NULL
	              OR gb.po_no = ''
	          )

	          AND pcb.active = TRUE
	          AND pcb.cancel = FALSE

	          AND NOT EXISTS (
	              SELECT 1
	              FROM purchase_bill_basic pbb
	              WHERE pbb.grn_no = gb.doc_id
	                AND pbb.org_id = gb.org_id
	                AND pbb.branch = gb.branch
	                AND pbb.active = TRUE
	                AND pbb.cancel = FALSE
	          )
	        """,
	        nativeQuery = true)
	List<Object[]> GrnNoDropdownforPurchaseBill(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch,
	        @Param("supplier") Long supplier);

//    item dropdown for the purchasebill

	@Query(value = """
	        SELECT
	            i.item_id AS item,
	            i.item_code AS itemCode,
	            i.item_description AS itemdesc,

	            h.hsn_id AS hsn_id,
	            h.hsn AS hsn_value,
	            gd.tax_percentage AS gst_rate,

	            gd.challan_qty AS challan_qty,

	            u.unitmaster_id AS unit_id,
	            u.description AS unit_value,

	            gd.received_qty AS received_qty,
	            gd.accept_qty AS accepted_qty,
	            gd.reject_qty AS rejected_qty,

	            gd.po_qty - gd.accept_qty AS shortage_qty,
	            gd.po_qty AS po_qty,

	            gd.po_rate AS po_rate,
	            gd.accept_qty * gd.po_rate AS amount,

	            gd.cgst_rate AS cgst_rate,

	            gd.sgst_rate AS sgst_rate,

	            gd.igst_rate AS igst_rate,

	            gd.tax_type AS tax_type

	        FROM grn_basic gb

	        INNER JOIN grn_details gd
	            ON gd.grn_basic_id = gb.grn_basic_id

	        INNER JOIN item i
	            ON i.item_id = gd.item

	        INNER JOIN listofvaluesdetails inspectionLov
	            ON inspectionLov.listofvaluesdetails_id = i.inspection

	        LEFT JOIN unitmaster u
	            ON u.unitmaster_id = gd.received_unit
	            AND u.active = TRUE
	            AND u.cancel = FALSE
				AND LOWER(gb.grn_type) = 'import'
	        LEFT JOIN hsn h
	            ON h.hsn_id = i.hsn_code
	            AND h.active = TRUE
	            AND h.cancel = FALSE

	        WHERE gb.org_id = :orgId
	          AND gb.branch = :branch
	          AND gb.supplier_code = :supplier
	          AND gb.doc_id = :grnNo

	          AND gb.active = TRUE
	          AND gb.cancel = FALSE

	          AND i.active = TRUE
	          AND i.cancel = FALSE

	          AND LOWER(inspectionLov.value_description)
	              IN ('sample', 'not required')
	        """,
	        nativeQuery = true)
	List<Object[]> GetItemDropDownForPurchaseBill(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch,
	        @Param("supplier") Long supplier,
	        @Param("grnNo") String grnNo);

	@Query(value = """
	        SELECT
	            i.item_id AS item,
	            i.item_code AS item_code,
	            i.item_description AS item_description,

	            igd.challan_qty AS challan_qty,
	            igd.received_qty AS grn_qty,
	            igd.acpt_qty AS accepted_qty,
	            igd.short_qty AS shortage_qty,

	            igd.fob_rate_fc AS fob_rate_fc

	        FROM import_grn_details igd

	        INNER JOIN grn_basic gb
	            ON gb.grn_basic_id = igd.grn_basic_id

	        INNER JOIN item i
	            ON i.item_id = igd.item

	        WHERE gb.org_id = :orgId
	          AND gb.branch = :branch
	          AND gb.supplier_code = :supplier
	          AND gb.doc_id = :grnNo

	          AND LOWER(gb.grn_type) = 'import'

	          AND gb.active = TRUE
	          AND gb.cancel = FALSE

	          AND i.active = TRUE
	          AND i.cancel = FALSE
	        """,
	        nativeQuery = true)
	List<Object[]> getImportItemDropDownForPurchaseBill(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch,
	        @Param("supplier") Long supplier,
	        @Param("grnNo") String grnNo);
	

}