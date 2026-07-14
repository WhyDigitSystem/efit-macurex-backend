package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProcessNonConformanceReportAttachmentVO;
import com.efitops.basesetup.entity.ProcessNonConformanceReportVO;

@Repository
public interface ProcessNonConformanceReportAttachmentRepo
		extends JpaRepository<ProcessNonConformanceReportAttachmentVO, Long> {

	List<ProcessNonConformanceReportAttachmentVO> findByProcessNonConformanceReportVO(
			ProcessNonConformanceReportVO processNonConformanceReportVO);

}
