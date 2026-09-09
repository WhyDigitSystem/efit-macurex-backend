package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ControlPlanParameterVO;
import com.efitops.basesetup.entity.ControlPlanVO;

public interface ControlPlanParameterRepo extends JpaRepository<ControlPlanParameterVO, Long> {
	
	
	 List<ControlPlanParameterVO> findByControlPlanVO(
	            ControlPlanVO controlPlanVO);

}
