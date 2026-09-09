package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ControlPlanDetailVO;
import com.efitops.basesetup.entity.ControlPlanVO;

public interface ControlPlanDetailRepo extends JpaRepository<ControlPlanDetailVO, Long> {
	
	
	 List<ControlPlanDetailVO> findByControlPlanVO(
	            ControlPlanVO controlPlanVO);

}
