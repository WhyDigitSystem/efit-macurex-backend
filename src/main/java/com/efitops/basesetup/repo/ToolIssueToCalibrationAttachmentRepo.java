package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ToolIssueToCalibrationAttachmentVO;
import com.efitops.basesetup.entity.ToolsIssueToCalibrationVO;

@Repository
public interface ToolIssueToCalibrationAttachmentRepo extends JpaRepository<ToolIssueToCalibrationAttachmentVO, Long> {

	List<ToolIssueToCalibrationAttachmentVO> findByToolsIssueToCalibrationVO(
			ToolsIssueToCalibrationVO toolsIssueToCalibrationVO);

}
