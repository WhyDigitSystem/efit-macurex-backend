package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.InstrumentCalibrationDetailsVO;
import com.efitops.basesetup.entity.InstrumentCalibrationVO;

public interface InstrumentCalibrationDetailsRepo extends JpaRepository<InstrumentCalibrationDetailsVO, Long>{

	List<InstrumentCalibrationDetailsVO> findByInstrumentCalibrationVO(InstrumentCalibrationVO instrumentCalibrationVO);

}
