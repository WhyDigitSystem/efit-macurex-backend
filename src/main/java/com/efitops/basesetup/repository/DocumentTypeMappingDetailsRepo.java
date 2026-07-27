package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;

public interface DocumentTypeMappingDetailsRepo
extends JpaRepository<DocumentTypeMappingDetailsVO, Long> {

List<DocumentTypeMappingDetailsVO> findByDocumentTypeMappingMasterVOId(Long id);

}