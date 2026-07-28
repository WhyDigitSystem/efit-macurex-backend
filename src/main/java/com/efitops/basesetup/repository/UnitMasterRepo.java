package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.UnitMasterVO;

public interface UnitMasterRepo extends JpaRepository<UnitMasterVO, Long> {

	boolean existsByOrgIdAndUnitIdIgnoreCase(Long orgId, String unitId);

	@Query(value = """
	        SELECT *
	        FROM unitmaster
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = false
	          AND active = true
	        ORDER BY unit_id
	        """, nativeQuery = true)
	List<UnitMasterVO> findByOrgIdAndBranch(@Param("orgId") Long orgId,
	                                        @Param("branch") Long branch);
}
