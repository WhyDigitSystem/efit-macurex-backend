package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ToolsIssueToCalibrationDetailsVO;
import com.efitops.basesetup.entity.ToolsIssueToCalibrationVO;

@Repository
public interface ToolsIssueToCalibrationDetailsRepo extends JpaRepository<ToolsIssueToCalibrationDetailsVO, Long> {

	List<ToolsIssueToCalibrationDetailsVO> findByToolsIssueToCalibrationVO(	ToolsIssueToCalibrationVO toolsIssueToCalibrationVO);

	
}
