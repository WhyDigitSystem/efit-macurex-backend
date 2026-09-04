package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ControlPlanDetailVO;

public interface ControlPlanDetailRepo extends JpaRepository<ControlPlanDetailVO, Long
> {

}
