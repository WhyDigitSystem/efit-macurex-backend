package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.SalesContractAmdDetailsVO;
import com.efitops.basesetup.entity.SalesContractAmendmentVO;

public interface SalesContractAmdRepo extends JpaRepository<SalesContractAmendmentVO, Long> {

	@Query(value = """
	        SELECT *
	        FROM sale_contract_amendment_basic
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = false and active = 1
	        ORDER BY sale_contract_amendment_basic_id
	        """, nativeQuery = true)
	List<SalesContractAmendmentVO> getSalesContractAmendmentByOrgId(@Param("orgId") Long orgId,@Param("branch") Long branch);
	
	@Query(nativeQuery = true, value = """
	        SELECT CONCAT(prefix, LPAD(last_no, 5, 0)) AS docid
	        FROM documenttypemapping_details
	        WHERE org_id = ?1
	          AND fin_year = ?2
	          AND screen_code = ?3
	        """)
	String getSalesContractAmendmentDocId(
	        Long orgId,
	        String financialYear,
	        String screenCode);

//	List<SalesContractAmdDetailsVO> findBySalesContractAmendmentVO(SalesContractAmendmentVO salesContractAmendmentVO);

}
