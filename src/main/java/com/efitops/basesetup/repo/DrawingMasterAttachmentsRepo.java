package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.DrawingMasterAttachmentsVO;
import com.efitops.basesetup.entity.DrawingMasterVO;

public interface DrawingMasterAttachmentsRepo extends JpaRepository<DrawingMasterAttachmentsVO, Long>{

	List<DrawingMasterAttachmentsVO> findByDrawingMasterVO(DrawingMasterVO drawingMasterVO);


}