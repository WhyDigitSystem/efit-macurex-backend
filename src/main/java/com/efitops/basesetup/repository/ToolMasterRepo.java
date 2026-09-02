package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.ToolMasterVO;

public interface ToolMasterRepo extends JpaRepository<ToolMasterVO, Long>{
	@Query("SELECT t FROM ToolMasterVO t " +
		       "WHERE t.orgId = :orgId " +
		       "AND t.branch.id = :branch")
		List<ToolMasterVO> getToolMasterByOrgId(
		        @Param("orgId") Long orgId,
		        @Param("branch") Long branch);

}
