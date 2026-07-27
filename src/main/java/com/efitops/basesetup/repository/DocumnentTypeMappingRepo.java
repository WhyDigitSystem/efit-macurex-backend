package com.efitops.basesetup.repository;

	import java.util.List;

	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.data.jpa.repository.Query;
	import org.springframework.data.repository.query.Param;

	import com.efitops.basesetup.entity.DocumentTypeMappingVO;

	public interface DocumnentTypeMappingRepo extends JpaRepository<DocumentTypeMappingVO, Long> {

	    @Query(value = """
	            SELECT *
	            FROM documenttypemappingmaster
	            WHERE org_id = :orgId
	              AND branch = :branch
	              AND cancel = false
	              AND active = true
	            ORDER BY documenttypemappingmaster_id
	            """, nativeQuery = true)
	    List<DocumentTypeMappingVO> findByOrgIdAndBranch(
	            @Param("orgId") Long orgId,
	            @Param("branch") Long branch);

		

		boolean existsByBranch_IdAndFinancialYear_IdAndOrgId(Object branch, Object financialYear, Object orgId);

	}


