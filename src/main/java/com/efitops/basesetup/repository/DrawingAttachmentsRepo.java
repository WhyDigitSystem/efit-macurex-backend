package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.DrawingAttachmentsVO;

public interface DrawingAttachmentsRepo extends JpaRepository<DrawingAttachmentsVO, Long> {

	List<DrawingAttachmentsVO> findByOrgIdAndCancelFalse(Long orgId);

}
