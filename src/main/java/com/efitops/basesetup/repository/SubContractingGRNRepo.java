package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractingGRNVO;

@Repository
public interface SubContractingGRNRepo extends JpaRepository<SubContractingGRNVO, Long>{

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getSubContractingGRNDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
	        SELECT *
	        FROM sub_contracting_grn
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND active = 1
	          AND cancel = 0
	        ORDER BY sub_contracting_grn_id DESC
	        """, nativeQuery = true)
	List<SubContractingGRNVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
	
	 @Query(value = """
	            SELECT
	                a.gate_pass_no AS grnNo,
	                a.sub_contracting_grn_id AS subGrnBasicId,
	                a.belongs_to AS under
	            FROM sub_contracting_grn a
	            INNER JOIN customer_header p
	                ON a.vendor = p.customer_id
	            WHERE a.cancel = FALSE
	              AND p.customer_code = :venid
	              AND :recid = 0
	              AND a.gate_pass_no IN (
	                    SELECT iib.mrin_grn_no
	                    FROM inward_inspection_basic iib
	                    WHERE iib.result = 'Accepted'
	              )
	              AND NOT EXISTS (
	                    SELECT 1
	                    FROM sc_bill_basic sb
	                    WHERE sb.grn_no = a.gate_pass_no
	              )

	            UNION

	            SELECT
	                a.gate_pass_no AS grnNo,
	                a.sub_contracting_grn_id AS subGrnBasicId,
	                a.belongs_to AS under
	            FROM sub_contracting_grn a
	            INNER JOIN customer_header p
	                ON a.vendor = p.customer_id
	            WHERE a.cancel = FALSE
	              AND p.customer_code = :venid
	              AND :recid = 0
	              AND a.gate_pass_no NOT IN (
	                    SELECT iib.mrin_grn_no
	                    FROM inward_inspection_basic iib
	              )
	              AND NOT EXISTS (
	                    SELECT 1
	                    FROM sc_bill_basic sb
	                    WHERE sb.grn_no = a.gate_pass_no
	              )

	            UNION

	            SELECT
	                a.gate_pass_no AS grnNo,
	                a.sub_contracting_grn_id AS subGrnBasicId,
	                a.belongs_to AS under
	            FROM sub_contracting_grn a
	            INNER JOIN sc_bill_basic b
	                ON a.gate_pass_no = b.grn_no
	            WHERE a.cancel = FALSE
	              AND a.gate_pass_no = :oldgrnno
	              AND :recid > 0

	            ORDER BY subGrnBasicId
	            """, nativeQuery = true)
	    List<Object[]> getGrnDropdown(
	            @Param("venid") String venid,
	            @Param("recid") Long recid,
	            @Param("oldgrnno") String oldgrnno);
	    
	    
//getSubContractingGrnForInwardInspectionReport
	    
	    
	    @Query(value = """
	    	    SELECT
	    	        a.sub_contracting_grn_id AS subgrnbasicid,
	    	        a.doc_id AS grnno,
	    	        a.doc_date AS grndt,
	    	        l.location_id AS locid,
	    	        pm.customer_code AS partyid,
	    	        i.item_code AS itemid,
	    	        u.unit_id AS unitid,
	    	        d.job_order_qty AS ordqty,
	    	        d.received_qty AS recqty,
	    	        p.code AS plantid,
	    	        i.manufactured_or_boughtout AS itype
	    	    FROM sub_contracting_grn a
	    	    INNER JOIN sub_contracting_grn_details d
	    	        ON a.sub_contracting_grn_id = d.sub_contracting_grn_id
	    	    INNER JOIN item i
	    	        ON d.incoming_item = i.item_id
	    	    INNER JOIN branch p
	    	        ON p.branch_id = a.branch
	    	    INNER JOIN location l
	    	        ON l.id = d.location
	    	    INNER JOIN customer_header pm
	    	        ON a.vendor = pm.customer_id
	    	    INNER JOIN unitmaster u
	    	        ON i.primary_unit = u.unitmaster_id
	    	    INNER JOIN (
	    	        SELECT
	    	            id.accept_qty AS accqty,
	    	            id.reject_qty AS rejqty,
	    	            ib.mrin_grn_no AS mrinno,
	    	            id.item AS itemid
	    	        FROM inward_inspection_basic ib
	    	        INNER JOIN inward_inspection_details id
	    	            ON ib.inward_inspection_basic_id =
	    	               id.inward_inspection_basic_id
	    	        WHERE ib.cancel = b'0'
	    	           OR ib.cancel IS NULL
	    	    ) insp
	    	        ON insp.mrinno = a.doc_id
	    	       AND insp.itemid = i.item_id
	    	    WHERE (a.cancel = b'0' OR a.cancel IS NULL)
	    	      AND a.doc_date <= :asOnDate
	    	      AND d.inspectionable = 'Yes'
	    	      AND (
	    	          d.received_qty - (insp.accqty + insp.rejqty)
	    	      ) <> 0

	    	    UNION

	    	    SELECT
	    	        a.sub_contracting_grn_id AS subgrnbasicid,
	    	        a.doc_id AS grnno,
	    	        a.doc_date AS grndt,
	    	        l.location_id AS locid,
	    	        pm.customer_code AS partyid,
	    	        i.item_code AS itemid,
	    	        u.unit_id AS unitid,
	    	        d.job_order_qty AS ordqty,
	    	        d.received_qty AS recqty,
	    	        p.code AS plantid,
	    	        i.manufactured_or_boughtout AS itype
	    	    FROM sub_contracting_grn a
	    	    INNER JOIN sub_contracting_grn_details d
	    	        ON a.sub_contracting_grn_id = d.sub_contracting_grn_id
	    	    INNER JOIN item i
	    	        ON d.incoming_item = i.item_id
	    	    INNER JOIN branch p
	    	        ON p.branch_id = a.branch
	    	    INNER JOIN location l
	    	        ON l.id = d.location
	    	    INNER JOIN customer_header pm
	    	        ON a.vendor = pm.customer_id
	    	    INNER JOIN unitmaster u
	    	        ON i.primary_unit = u.unitmaster_id
	    	    WHERE (a.cancel = b'0' OR a.cancel IS NULL)
	    	      AND a.doc_date <= :asOnDate
	    	      AND d.inspectionable = 'Yes'
	    	      AND NOT EXISTS (
	    	          SELECT 1
	    	          FROM inward_inspection_basic ib
	    	          INNER JOIN inward_inspection_details id
	    	              ON ib.inward_inspection_basic_id =
	    	                 id.inward_inspection_basic_id
	    	          WHERE ib.mrin_grn_no = a.doc_id
	    	            AND id.item = i.item_id
	    	      )
	    	    ORDER BY 3, 2, 4, 5, 6
	    	    """, nativeQuery = true)
	    	List<Object[]> getSubContractingGrnForInwardInspectionReport(
	    	        @Param("asOnDate") String asOnDate);

}