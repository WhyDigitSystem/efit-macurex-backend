package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BomCorrectionRequestNoteDetailsVO;
import com.efitops.basesetup.entity.BomCorrectionRequestNoteVO;

@Repository
public interface BomCorrectionRequestNoteDetailsRepo extends JpaRepository< BomCorrectionRequestNoteDetailsVO, Long>{

	List<BomCorrectionRequestNoteDetailsVO> findByBomCorrectionRequestNoteVO(
			BomCorrectionRequestNoteVO bomCorrectionRequestNoteVO);

}
