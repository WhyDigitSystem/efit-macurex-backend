package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PdfAttachmentDrawingVO;

@Repository
public interface PdfAttachmentDrawingRepo extends JpaRepository<PdfAttachmentDrawingVO, Long> {

}
