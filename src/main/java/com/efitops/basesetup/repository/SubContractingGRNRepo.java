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

}