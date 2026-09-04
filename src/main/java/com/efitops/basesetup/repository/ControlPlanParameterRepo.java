package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ControlPlanParameterVO;

public interface ControlPlanParameterRepo extends JpaRepository<ControlPlanParameterVO, Long> {

}
