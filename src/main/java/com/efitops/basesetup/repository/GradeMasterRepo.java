package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.GradeMasterVO;

public interface GradeMasterRepo extends JpaRepository<GradeMasterVO, Long> {

	boolean existsByOrgIdAndGradeCodeIgnoreCase(Long orgId, String gradeCode);
	@Query(value = """
	        SELECT *
	        FROM grademaster
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = false
	          AND active = true
	        ORDER BY grade_code
	        """, nativeQuery = true)
	List<GradeMasterVO> findByOrgIdAndBranch(@Param("orgId") Long orgId,
	                                         @Param("branch") Long branch);

	
}
