package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ControlPlanSampleVO;
import com.efitops.basesetup.entity.ControlPlanVO;

public interface ControlPlanSampleRepo extends JpaRepository<ControlPlanSampleVO, Long>{
	
	
	 List<ControlPlanSampleVO> findByControlPlanVO(
	            ControlPlanVO controlPlanVO);

}
