package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.GateInwardEntryVO;

public interface GateInwardEntryRepo extends JpaRepository<GateInwardEntryVO, Long> {

	List<GateInwardEntryVO> findByBranchIdAndOrgIdAndCancelFalse(
	        Long branch,
	        Long orgId);

//	customername dropdown 
	@Query(value = "SELECT " +
	        "c.customer_name, " +
	        "c.address, " +
	        "c.customer_id, " +
	        "c.customer_code " +
	        "FROM customer_header c " +
	        "WHERE c.cancel = FALSE " +
	        "AND c.active = TRUE " +
	        "AND c.branch = :branch " +
	        "AND c.org_id = :orgId " +
	        "ORDER BY c.customer_name",
	        nativeQuery = true)
	List<Object[]> getCustomerNameDropdownForGateInwardEntry(
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId);

	@Query(value = """
	        SELECT
	            doc_id,
	            doc_date,
	            supplier_invoice_number,
	            supplier_invoice_date
	        FROM gate_inward_entry_basic
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND customer = :customer and active=1 and cancel=0
	        """, nativeQuery = true)
	Set<Object[]> getGateInwardEntryDropdown(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch,
	        @Param("customer") Long customer);
	
//	gate inward details 
	@Query(value = """
	        SELECT
	            gb.doc_id AS GPNO,
	            gb.doc_date AS GPDATE,
	            p.customer_code AS PARTYID,
	            gb.supplier_invoice_number AS SUPDCNO,
	            gb.supplier_invoice_date AS SUPDCDT,
	            p.customer_name AS PARTYNAME,
	            i.item_code AS ITEMID,
	            i.item_description AS ITEMDESC,
	            u.unit_id AS UNITID,
	            pd.challan_qty AS GPQTY,
	            gb.invoice_number AS invctr,
	            gb.modvat_copy_received AS RHH,
	            pb.po_no AS pono
	        FROM gate_inward_entry_basic gb
	        INNER JOIN grn_basic pb
	            ON gb.doc_id = pb.gate_pass_no
	        INNER JOIN grn_details pd
	            ON pb.grn_basic_id = pd.grn_basic_id
	        INNER JOIN customer_header p
	            ON gb.customer = p.customer_id
	        INNER JOIN item i
	            ON pd.item = i.item_id
	        LEFT JOIN unitmaster u
	            ON i.primary_unit = u.unitmaster_id
	        WHERE COALESCE(pb.cancel, 0) = 0
	          AND gb.branch = :branch
	          AND gb.org_id = :orgId
	          AND gb.doc_date BETWEEN :startDate AND :endDate

	        UNION

	        SELECT
	            gb.doc_id AS GPNO,
	            gb.doc_date AS GPDATE,
	            p.customer_code AS PARTYID,
	            gb.supplier_invoice_number AS SUPDCNO,
	            gb.supplier_invoice_date AS SUPDCDT,
	            p.customer_name AS PARTYNAME,
	            i.item_code AS ITEMID,
	            i.item_description AS ITEMDESC,
	            u.unit_id AS UNITID,
	            pd.gate_pass_qty AS GPQTY,
	            gb.invoice_number AS invctr,
	            gb.modvat_copy_received AS RHH,
	            pb.contract_no AS pono
	        FROM gate_inward_entry_basic gb
	        INNER JOIN sub_contracting_grn pb
	            ON gb.gate_inward_entry_basic_id = pb.gate_pass_no
	        INNER JOIN sub_contracting_grn_details pd
	            ON pb.sub_contracting_grn_id = pd.sub_contracting_grn_id
	        LEFT JOIN customer_header p
	            ON gb.customer = p.customer_id
	        LEFT JOIN item i
	            ON pd.incoming_item = i.item_id
	        LEFT JOIN unitmaster u
	            ON i.primary_unit = u.unitmaster_id
	        WHERE COALESCE(pb.cancel, 0) = 0
	          AND gb.branch = :branch
	          AND gb.org_id = :orgId
	          AND gb.doc_date BETWEEN :startDate AND :endDate

	        ORDER BY GPDATE
	        """, nativeQuery = true)
	List<Object[]> getGateInwarddetails(
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId,
	        @Param("startDate") String startDate,
	        @Param("endDate") String endDate
	);
	
	@Query(value = """
	        SELECT
	            gb.doc_id AS GPNO,
	            gb.doc_date AS GPDATE,
	            p.customer_code AS PARTYID,
	            gb.modvat_copy_received AS modvat,
	            gb.supplier_invoice_number AS SUPDCNO,
	            gb.supplier_invoice_date AS SUPDCDT,
	            p.customer_name AS PARTYNAME,
	            i.item_code AS ITEMID,
	            i.item_description AS ITEMDESC,
	            u.unit_id AS UNITID,
	            pd.challan_qty AS GPQTY
	        FROM gate_inward_entry_basic gb
	        INNER JOIN grn_basic pb
	            ON gb.doc_id = pb.gate_pass_no
	        INNER JOIN grn_details pd
	            ON pb.grn_basic_id = pd.grn_basic_id
	        INNER JOIN customer_header p
	            ON gb.customer = p.customer_id
	        INNER JOIN item i
	            ON pd.item = i.item_id
	        LEFT JOIN unitmaster u
	            ON i.primary_unit = u.unitmaster_id
	        WHERE COALESCE(pb.cancel, 0) = 0
	          AND gb.branch = :branch
	          AND gb.org_id = :orgId
	          AND gb.modvat_copy_received = 'yes'
	          AND gb.doc_date BETWEEN :startDate AND :endDate

	        UNION

	        SELECT
	            gb.doc_id AS GPNO,
	            gb.doc_date AS GPDATE,
	            p.customer_code AS PARTYID,
	            gb.modvat_copy_received AS modvat,
	            gb.supplier_invoice_number AS SUPDCNO,
	            gb.supplier_invoice_date AS SUPDCDT,
	            p.customer_name AS PARTYNAME,
	            i.item_code AS ITEMID,
	            i.item_description AS ITEMDESC,
	            u.unit_id AS UNITID,
	            pd.gate_pass_qty AS GPQTY
	        FROM gate_inward_entry_basic gb
	        INNER JOIN sub_contracting_grn pb
	            ON gb.gate_inward_entry_basic_id = pb.gate_pass_no
	        INNER JOIN sub_contracting_grn_details pd
	            ON pb.sub_contracting_grn_id = pd.sub_contracting_grn_id
	        LEFT JOIN customer_header p
	            ON gb.customer = p.customer_id
	        LEFT JOIN item i
	            ON pd.incoming_item = i.item_id
	        LEFT JOIN unitmaster u
	            ON i.primary_unit = u.unitmaster_id
	        WHERE COALESCE(pb.cancel, 0) = 0
	          AND gb.branch = :branch
	          AND gb.org_id = :orgId
	          AND gb.modvat_copy_received = 'no'
	          AND gb.doc_date BETWEEN :startDate AND :endDate

	        ORDER BY 7, 1, 8
	        """, nativeQuery = true)
	List<Object[]> getGateInwardModvatReport(
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId,
	        @Param("startDate") String startDate,
	        @Param("endDate") String endDate
	);

}
