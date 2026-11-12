package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.DocumentTypeMappingVO;

public interface DocumentTypeMappingRepo extends JpaRepository<DocumentTypeMappingVO, Long> {

	@Query(value = """
			SELECT *
			FROM document_type_mapping
			WHERE org_id = :orgId
			  AND branch = :branch
			  AND cancel = false
			  AND active = true
			ORDER BY document_type_mapping_id
			""", nativeQuery = true)
	List<DocumentTypeMappingVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branch") Long branch);

	boolean existsByBranch_IdAndFinancialYear_IdAndOrgId(Object branch, Object financialYear, Object orgId);

	@Query(nativeQuery = true, value = "SELECT     b.screen_name,     b.screen_code, \r\n"
			+ "			    b.doc_code,    ?4 finyear,    ?2 AS branch,    ?3 AS branch_code,\r\n"
			+ "			    ?5 AS finyearidentifier,    CONCAT(?3,'/', b.screen_code,'/',?5,'/') AS prefixfield FROM (\r\n"
			+ "			    SELECT doc_code, screen_code, screen_name     FROM document_type_master  \r\n"
			+ "			    WHERE CONCAT(screen_code, doc_code) NOT IN (SELECT CONCAT(screen_code, doc_code) \r\n"
			+ "			        FROM documenttypemapping_details   WHERE fin_year =?4 and org_id = ?1\r\n"
			+ "			          AND branch = ?2) and org_id =?1) b")
	Set<Object[]> getPendingDoctypeMapping(Long orgId, String branch, String branchCode, String finYear,
			String finYearIdentifier);

	@Query(value = "select a from DocumentTypeMappingVO a where a.orgId=?1")
	List<DocumentTypeMappingVO> findByOrgId(Long orgId);

}
