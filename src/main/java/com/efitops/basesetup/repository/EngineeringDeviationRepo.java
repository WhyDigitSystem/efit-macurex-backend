package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.EngineeringDeviationRequestVO;

public interface EngineeringDeviationRepo extends JpaRepository<EngineeringDeviationRequestVO, Long>{

}
