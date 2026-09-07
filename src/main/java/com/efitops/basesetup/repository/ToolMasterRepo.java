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
	
	@Query(value = """
		    SELECT l.id as locationId,
		        l.location_name AS locationName
		    FROM location l
		    WHERE l.branch = :branch
		      AND l.org_id = :orgId
		      AND l.active = 1
		      AND  l.cancel = 0 
		    """, nativeQuery = true)
		List<Object[]> getLocationForToolMaster(
		        @Param("orgId") Long orgId,
		        @Param("branch") Long branch
		);

}
