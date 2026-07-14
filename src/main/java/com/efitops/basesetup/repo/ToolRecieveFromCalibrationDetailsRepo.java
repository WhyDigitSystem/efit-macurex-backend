package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ToolRecieveFromCalibrationDetailsVO;
import com.efitops.basesetup.entity.ToolRecieveFromCalibrationVO;

@Repository
public interface ToolRecieveFromCalibrationDetailsRepo
		extends JpaRepository<ToolRecieveFromCalibrationDetailsVO, Long> {

	List<ToolRecieveFromCalibrationDetailsVO> findByToolRecieveFromCalibrationVO(
			ToolRecieveFromCalibrationVO toolRecieveFromCalibrationVO);
}
