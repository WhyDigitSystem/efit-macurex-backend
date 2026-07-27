package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DocumentTypeMappingDetailsVO;

@Repository
public interface DocumentTypeMappingDetailsRepo
extends JpaRepository<DocumentTypeMappingDetailsVO, Long> {

List<DocumentTypeMappingDetailsVO> findByDocumentTypeMappingMasterVOId(Long id);

}