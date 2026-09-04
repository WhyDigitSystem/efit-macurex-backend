package com.efitops.basesetup.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.MachineMasterVO;

public interface MachineMasterRepo extends JpaRepository<MachineMasterVO, Long> {

	List<MachineMasterVO> findByOrgIdAndBranch(Long orgId, BranchVO branchVO);
	
	@Query(value = """
	        SELECT
	            tcd.tool_category_detail_id AS toolCategoryId,
	            tcd.category
	        FROM tool_category_basic tcb
	        INNER JOIN tool_category_detail tcd
	            ON tcd.tool_category_basic_id = tcb.tool_category_basic_id
	        WHERE tcb.apllicable_for = :applicableFor
	          AND tcb.org_id = :orgId
	        ORDER BY tcd.category
	        """, nativeQuery = true)
	List<Object[]> getToolCategoryforMachineMaster(
	        @Param("orgId") Long orgId,
	        @Param("applicableFor") String applicableFor);
	
	
}
	       
