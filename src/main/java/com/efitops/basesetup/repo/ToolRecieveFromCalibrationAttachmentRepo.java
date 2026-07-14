package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ToolRecieveFromCalibrationAttachmentVO;
import com.efitops.basesetup.entity.ToolRecieveFromCalibrationVO;

@Repository
public interface ToolRecieveFromCalibrationAttachmentRepo extends JpaRepository<ToolRecieveFromCalibrationAttachmentVO, Long> {

	List<ToolRecieveFromCalibrationAttachmentVO> findByToolRecieveFromCalibrationVO(
			ToolRecieveFromCalibrationVO toolRecieveFromCalibrationVO);

}
