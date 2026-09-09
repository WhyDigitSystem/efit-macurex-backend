package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.ProcessSheetCompRoutingVO;

public interface ProcessSheetCompRoutingRepo extends JpaRepository<ProcessSheetCompRoutingVO, Long> {

	

	@Query("SELECT p FROM ProcessSheetCompRoutingVO p " +
		       "WHERE p.orgId = :orgId " +
		       "AND p.branch.id = :branch")
		List<ProcessSheetCompRoutingVO> findByOrgIdAndBranch(
		        @Param("orgId") Long orgId,
		        @Param("branch") Long branch);
	
	
	
}
