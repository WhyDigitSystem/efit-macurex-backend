package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.EnquiryAttachmentVO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.IncomingAttachmentVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;

public interface IncomingAttachmentRepo extends JpaRepository<IncomingAttachmentVO, Long>{
	void deleteById(Long id);


	List<IncomingAttachmentVO> findByIncomingMaterialInspectionVO(IncomingMaterialInspectionVO incomingMaterialInspectionVO);


	void deleteByIncomingMaterialInspectionVO(IncomingMaterialInspectionVO incomingMaterialInspectionVO);
}
