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
	    	
	    	
	    	// Sub Contracting GRN Pending List for Inspection

	    	@Query(value = """
	    	    SELECT
	    	        pb.doc_id AS GRNNO,
	    	        pb.doc_date AS GRNDT,
	    	        pb.grn_clear_time AS stime,
	    	        im.item_code AS ITEMID,
	    	        im.item_description AS ITEMDESC,
	    	        pd.received_qty AS RECQTY,
	    	        pd.job_order_rate AS RATE,
	    	        pd.accepted_qty AS accqty,
	    	        pd.rejected_qty AS rejqty,
	    	        pd.job_order_rate AS prate,
	    	        pm.customer_code AS PARTYID,
	    	        pm.customer_name AS PARTYNAME,
	    	        pb.supplier_dc_no AS supdcno,
	    	        pb.sub_contracting_grn_id AS subgrnBASICID,
	    	        CONCAT(pm.customer_code, '--', pm.customer_name) AS PAty,
	    	        CONCAT(pb.doc_id, '--', pb.doc_date, '--', pb.grn_clear_time) AS GRNID
	    	    FROM sub_contracting_grn pb
	    	    INNER JOIN sub_contracting_grn_details pd
	    	        ON pd.sub_contracting_grn_id = pb.sub_contracting_grn_id
	    	    INNER JOIN item im
	    	        ON im.item_id = pd.incoming_item
	    	    INNER JOIN customer_header pm
	    	        ON pm.customer_id = pb.vendor
	    	    INNER JOIN location ld
	    	        ON ld.id = pd.location
	    	    LEFT JOIN inward_inspection_basic iib
	    	        ON iib.mrin_grn_no = pb.doc_id
	    	       AND COALESCE(iib.cancel, 0) = 0
	    	    LEFT JOIN inward_inspection_details iid
	    	        ON iid.inward_inspection_basic_id = iib.inward_inspection_basic_id
	    	       AND iid.item = im.item_id
	    	    WHERE COALESCE(pb.cancel, 0) = 0
	    	      AND pb.doc_date BETWEEN :fromDate AND :toDate
	    	      AND (
	    	          pd.received_qty -
	    	          (COALESCE(iid.total_acc_qty_in_primary_unit, 0)
	    	           + COALESCE(iid.reject_qty, 0))
	    	      ) > 0
	    	      AND ld.id = :locId
	    	      AND pb.branch = :branch
	    	      AND pb.org_id = :orgId
	    	      AND pd.inspectionable = 'Yes'

	    	    UNION

	    	    SELECT
	    	        pb.doc_id AS GRNNO,
	    	        pb.doc_date AS GRNDT,
	    	        pb.grn_clear_time AS stime,
	    	        im.item_code AS ITEMID,
	    	        im.item_description AS ITEMDESC,
	    	        pd.received_qty AS RECQTY,
	    	        pd.job_order_rate AS RATE,
	    	        pd.accepted_qty AS accqty,
	    	        pd.rejected_qty AS rejqty,
	    	        pd.job_order_rate AS prate,
	    	        pm.customer_code AS PARTYID,
	    	        pm.customer_name AS PARTYNAME,
	    	        pb.supplier_dc_no AS supdcno,
	    	        pb.sub_contracting_grn_id AS subgrnBASICID,
	    	        CONCAT(pm.customer_code, '--', pm.customer_name) AS PAty,
	    	        CONCAT(pb.doc_id, '--', pb.doc_date, '--', pb.grn_clear_time) AS GRNID
	    	    FROM sub_contracting_grn pb
	    	    INNER JOIN sub_contracting_grn_details pd
	    	        ON pd.sub_contracting_grn_id = pb.sub_contracting_grn_id
	    	    INNER JOIN item im
	    	        ON im.item_id = pd.incoming_item
	    	    INNER JOIN customer_header pm
	    	        ON pm.customer_id = pb.vendor
	    	    INNER JOIN location ld
	    	        ON ld.id = pd.location
	    	    WHERE COALESCE(pb.cancel, 0) = 0
	    	      AND pb.doc_date BETWEEN :fromDate AND :toDate
	    	      AND pd.received_qty > 0
	    	      AND ld.id = :locId
	    	      AND pb.branch = :branch
	    	      AND pb.org_id = :orgId
	    	      AND pd.inspectionable = 'Yes'
	    	      AND NOT EXISTS (
	    	          SELECT 1
	    	          FROM inward_inspection_basic iib
	    	          INNER JOIN inward_inspection_details iid
	    	              ON iid.inward_inspection_basic_id =
	    	                 iib.inward_inspection_basic_id
	    	          WHERE COALESCE(iib.cancel, 0) = 0
	    	            AND iib.mrin_grn_no = pb.doc_id
	    	            AND iid.item = im.item_id
	    	      )
	    	    ORDER BY PARTYID, GRNID
	    	    """, nativeQuery = true)
	    	List<Object[]> getSubContractingGrnPendingListForInspection(
	    	        @Param("fromDate") String fromDate,
	    	        @Param("toDate") String toDate,
	    	        @Param("locId") Long locId,
	    	        @Param("branch") Long branch,
	    	        @Param("orgId") Long orgId
	    	);
	    	
	    	@Query(value = """
	    		    SELECT
	    		        ch.customer_name AS customer,
	    		        a.doc_id AS GRNNO,
	    		        a.doc_date AS GRNDT,
	    		        CONCAT(a.doc_id, '-', a.doc_date) AS grnno1,
	    		        a.grn_clear_time AS time,
	    		        a.supplier_dc_no AS supdcno,
	    		        a.supplier_dc_date AS supdcdt,
	    		        ch.customer_name AS PARTYNAME,
	    		        i.item_description AS ITEMDESC,
	    		        u.unit_id AS UNITID,
	    		        b.gate_pass_qty AS BATCHQTY,
	    		        b.received_qty AS RECQTY,
	    		        b.accepted_qty AS ACCQTY,
	    		        b.rejected_qty AS REJQTY,
	    		        i.item_code AS itemid,
	    		        b.job_order_rate AS prate,
	    		        b.job_order_rate AS jormcost,
	    		        ch.customer_code AS partyid,
	    		        ch.customer_company_code AS buycode,
	    		        a.remarks AS remarks,
	    		        a.sub_contracting_grn_id AS SUBGRNBASICid
	    		    FROM sub_contracting_grn a
	    		    INNER JOIN sub_contracting_grn_details b
	    		        ON a.sub_contracting_grn_id = b.sub_contracting_grn_id
	    		    INNER JOIN customer_header ch
	    		        ON a.vendor = ch.customer_id
	    		    INNER JOIN item i
	    		        ON i.item_id = b.incoming_item
	    		    INNER JOIN location ld
	    		        ON ld.id = b.location
	    		    LEFT JOIN unitmaster u
	    		        ON u.unitmaster_id = b.primary_unit
	    		    WHERE COALESCE(a.cancel, 0) = 0
	    		      AND a.doc_date BETWEEN :fromDate AND :toDate
	    		      AND b.inspectionable = 'No'
	    		      AND ch.belongs_to = :plant
	    		      AND ld.id = :locId
	    		      AND a.branch = :branch
	    		      AND a.org_id = :orgId

	    		    UNION

	    		    SELECT
	    		        ch.customer_name AS customer,
	    		        a.doc_id AS GRNNO,
	    		        a.doc_date AS GRNDT,
	    		        CONCAT(a.doc_id, '-', a.doc_date) AS grnno1,
	    		        a.grn_clear_time AS time,
	    		        a.supplier_dc_no AS supdcno,
	    		        a.supplier_dc_date AS supdcdt,
	    		        ch.customer_name AS PARTYNAME,
	    		        i.item_description AS ITEMDESC,
	    		        u.unit_id AS UNITID,
	    		        b.gate_pass_qty AS BATCHQTY,
	    		        b.received_qty AS RECQTY,
	    		        b.accepted_qty AS ACCQTY,
	    		        b.rejected_qty AS REJQTY,
	    		        i.item_code AS itemid,
	    		        b.job_order_rate AS prate,
	    		        b.job_order_rate AS jormcost,
	    		        ch.customer_code AS partyid,
	    		        ch.customer_company_code AS buycode,
	    		        a.remarks AS remarks,
	    		        a.sub_contracting_grn_id AS SUBGRNBASICid
	    		    FROM sub_contracting_grn a
	    		    INNER JOIN sub_contracting_grn_details b
	    		        ON a.sub_contracting_grn_id = b.sub_contracting_grn_id
	    		    INNER JOIN customer_header ch
	    		        ON a.vendor = ch.customer_id
	    		    INNER JOIN item i
	    		        ON i.item_id = b.incoming_item
	    		    INNER JOIN location ld
	    		        ON ld.id = b.location
	    		    LEFT JOIN unitmaster u
	    		        ON u.unitmaster_id = b.primary_unit
	    		    WHERE COALESCE(a.cancel, 0) = 0
	    		      AND a.doc_date BETWEEN :fromDate AND :toDate
	    		      AND b.inspectionable = 'Yes'
	    		      AND ch.belongs_to = :plant
	    		      AND ld.id = :locId
	    		      AND a.branch = :branch
	    		      AND a.org_id = :orgId
	    		      AND NOT EXISTS (
	    		          SELECT 1
	    		          FROM inward_inspection_basic iib
	    		          INNER JOIN inward_inspection_details iid
	    		              ON iid.inward_inspection_basic_id =
	    		                 iib.inward_inspection_basic_id
	    		          WHERE COALESCE(iib.cancel, 0) = 0
	    		            AND iib.mrin_grn_no = a.doc_id
	    		            AND iid.item = i.item_id
	    		      )

	    		    UNION

	    		    SELECT
	    		        ch.customer_name AS customer,
	    		        a.doc_id AS GRNNO,
	    		        a.doc_date AS GRNDT,
	    		        CONCAT(a.doc_id, '-', a.doc_date) AS grnno1,
	    		        a.grn_clear_time AS time,
	    		        a.supplier_dc_no AS supdcno,
	    		        a.supplier_dc_date AS supdcdt,
	    		        ch.customer_name AS PARTYNAME,
	    		        i.item_description AS ITEMDESC,
	    		        u.unit_id AS UNITID,
	    		        b.gate_pass_qty AS BATCHQTY,
	    		        b.received_qty AS RECQTY,
	    		        iid.accept_qty AS ACCQTY,
	    		        iid.reject_qty AS REJQTY,
	    		        i.item_code AS itemid,
	    		        b.job_order_rate AS prate,
	    		        b.job_order_rate AS jormcost,
	    		        ch.customer_code AS partyid,
	    		        ch.customer_company_code AS buycode,
	    		        a.remarks AS remarks,
	    		        a.sub_contracting_grn_id AS SUBGRNBASICid
	    		    FROM sub_contracting_grn a
	    		    INNER JOIN sub_contracting_grn_details b
	    		        ON a.sub_contracting_grn_id = b.sub_contracting_grn_id
	    		    INNER JOIN customer_header ch
	    		        ON a.vendor = ch.customer_id
	    		    INNER JOIN item i
	    		        ON i.item_id = b.incoming_item
	    		    INNER JOIN location ld
	    		        ON ld.id = b.location
	    		    LEFT JOIN unitmaster u
	    		        ON u.unitmaster_id = b.primary_unit
	    		    INNER JOIN inward_inspection_basic iib
	    		        ON iib.mrin_grn_no = a.doc_id
	    		       AND COALESCE(iib.cancel, 0) = 0
	    		    INNER JOIN inward_inspection_details iid
	    		        ON iid.inward_inspection_basic_id =
	    		           iib.inward_inspection_basic_id
	    		       AND iid.item = i.item_id
	    		    WHERE COALESCE(a.cancel, 0) = 0
	    		      AND a.doc_date BETWEEN :fromDate AND :toDate
	    		      AND ch.belongs_to = :plant
	    		      AND ld.id = :locId
	    		      AND a.branch = :branch
	    		      AND a.org_id = :orgId

	    		    ORDER BY GRNNO, supdcno, RECQTY
	    		    """, nativeQuery = true)
	    		List<Object[]> getSubContractingGrnItemWiseReport(
	    		        @Param("fromDate") String fromDate,
	    		        @Param("toDate") String toDate,
	    		        @Param("plant") Long plant,
	    		        @Param("locId") Long locId,
	    		        @Param("branch") Long branch,
	    		        @Param("orgId") Long orgId
	    		);

}