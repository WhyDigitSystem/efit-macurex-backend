package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.DrawingAttachmentDetailVO;
import com.efitops.basesetup.entity.DrawingAttachmentsVO;

public interface DrawingAttachmentDetailRepo extends JpaRepository<DrawingAttachmentDetailVO, Long> {

	List<DrawingAttachmentDetailVO> findByDrawingAttachmentsVO(DrawingAttachmentsVO drawingAttachmentsVO);

}
