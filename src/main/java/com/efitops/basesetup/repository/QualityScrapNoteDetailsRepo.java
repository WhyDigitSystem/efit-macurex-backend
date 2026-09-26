package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.QualityScrapNoteDetailsVO;

public interface QualityScrapNoteDetailsRepo extends JpaRepository<QualityScrapNoteDetailsVO, Long>{

	List<QualityScrapNoteDetailsVO> findByQualityScrapNoteVOId(Long id);

}
