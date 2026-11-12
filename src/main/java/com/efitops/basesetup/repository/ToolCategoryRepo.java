package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ToolCategoryVO;

public interface ToolCategoryRepo extends JpaRepository<ToolCategoryVO, Long> {

	List<ToolCategoryVO> findByOrgId(Long orgId);

//	List<Object[]> getToolCategoryforMachineMaster(String type, Long orgId, Long branch);

}
