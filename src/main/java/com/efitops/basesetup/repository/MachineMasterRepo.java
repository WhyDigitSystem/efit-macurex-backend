package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.MachineMasterVO;

public interface MachineMasterRepo extends JpaRepository<MachineMasterVO, Long> {

	List<MachineMasterVO> findByOrgIdAndBranch(Long orgId, BranchVO branchVO);

}
