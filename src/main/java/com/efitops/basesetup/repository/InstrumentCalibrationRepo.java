package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.InstrumentCalibrationVO;

public interface InstrumentCalibrationRepo extends JpaRepository<InstrumentCalibrationVO, Long>{

}
