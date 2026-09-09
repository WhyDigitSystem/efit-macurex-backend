package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ControlPlanMachineFixtureVO;
import com.efitops.basesetup.entity.ControlPlanVO;

public interface ControlPlanMachineFixtureRepo extends JpaRepository<ControlPlanMachineFixtureVO, Long> {

	 List<ControlPlanMachineFixtureVO> findByControlPlanVO(
	            ControlPlanVO controlPlanVO);

}
