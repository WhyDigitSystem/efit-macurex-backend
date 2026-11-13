package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.GateOutwardEntryVO;

public interface GateOutwardEntryRepo extends JpaRepository<GateOutwardEntryVO, Long> {

	List<GateOutwardEntryVO> findByOrgIdAndCancelFalse(Long orgId);
	
	
	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid "
	        + "from documenttypemapping_details "
	        + "where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getGateOutwardEntryDocId(
	        Long orgId,
	        String financialYear,
	        String screenCode);
	
	
	@Query(value = """
	        SELECT DISTINCT
	            a.deliverychallan_subcontracting_id AS subcontbasicid,
	            a.doc_id AS DOCID,
	            a.doc_date AS DOCDATE,
	            c.customer_name AS PARTY,
	            a.deliverychallan_subcontracting_id AS BASICID
	        FROM deliverychallan_subcontracting a
	        INNER JOIN deliverychallan_subcontracting_details b
	            ON a.deliverychallan_subcontracting_id =
	               b.deliverychallan_subcontracting_id
	        INNER JOIN customer_header c
	            ON c.customer_id = a.vendor
	        WHERE a.cancel = FALSE
	          AND c.customer_id = :customer
	          AND :breakdown = 'NO'
	          AND a.branch = :branch
	          AND a.org_id = :orgId
	        ORDER BY a.doc_id
	        """, nativeQuery = true)
	List<Object[]> getChallanNoForGateOutwardEntry(
	        @Param("customer") Long customer,
	        @Param("breakdown") String breakdown,
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId
	);

}
