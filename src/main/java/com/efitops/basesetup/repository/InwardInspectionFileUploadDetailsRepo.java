package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InwardInspectionFileUploadDetailsVO;
import com.efitops.basesetup.entity.InwardInspectionVO;

@Repository
public interface InwardInspectionFileUploadDetailsRepo
		extends JpaRepository<InwardInspectionFileUploadDetailsVO, Long> {

	List<InwardInspectionFileUploadDetailsVO> findByInwardInspectionVO(InwardInspectionVO vo);

}
