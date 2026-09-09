package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ControlPlanVO;

public interface ControlPlanRepo extends JpaRepository<ControlPlanVO, Long	> {

	List<ControlPlanVO> findByOrgIdAndBranch(Long orgId, Long branch);

}
