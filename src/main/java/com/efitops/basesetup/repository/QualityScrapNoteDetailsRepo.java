package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.QualityScrapNoteDetailsVO;
import com.efitops.basesetup.entity.QualityScrapNoteVO;

public interface QualityScrapNoteDetailsRepo extends JpaRepository<QualityScrapNoteDetailsVO, Long>{

//	List<QualityScrapNoteDetailsVO> findByQualityScrapNoteVOId(QualityScrapNoteVO savedQualityScrapNote);

	List<QualityScrapNoteDetailsVO> findByQualityScrapNoteVO(QualityScrapNoteVO qualityScrapNoteVO);

}
