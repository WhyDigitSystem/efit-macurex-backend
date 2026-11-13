package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.DocumentTypeMasterVO;

public interface DocumentTypeMasterRepo extends JpaRepository<DocumentTypeMasterVO, Long> {

	@Query(value = """
			SELECT *
			FROM documenttypemaster
			WHERE org_id = :orgId
			  AND branch = :branch
			  AND cancel = false
			  AND active = true
			ORDER BY name
			""", nativeQuery = true)
	List<DocumentTypeMasterVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branch") Long branch);

//	boolean existsByOrgIdAndCodeIgnoreCase(Long orgId, String code);

	boolean existsByOrgIdAndScreenCode(Long orgId, String screenCode);

	boolean existsByOrgIdAndDocCode(Long orgId, String docCode);

	List<DocumentTypeMasterVO> findAllByOrgId(Long orgId);

}
