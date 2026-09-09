package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.RootCauseAnalysisVO;

public interface RootCauseAnalysisRepo extends JpaRepository<RootCauseAnalysisVO, Long> {

	@Query("SELECT r FROM RootCauseAnalysisVO r " +
		       "WHERE r.orgId = :orgId " +
		       "AND r.branch.id = :branch")
		List<RootCauseAnalysisVO> findByOrgIdAndBranch(
		        @Param("orgId") Long orgId,
		        @Param("branch") Long branch);
	
	
	
	
}
