package com.efitops.basesetup.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ScrapNoteReasonDetailsVO;
import com.efitops.basesetup.entity.ScrapNoteVO;

@Repository
public interface ScrapNoteReasonDetailsRepo extends JpaRepository<ScrapNoteReasonDetailsVO, Long> {
    List<ScrapNoteReasonDetailsVO> findByScrapNoteVO(ScrapNoteVO scrapNoteVO);
}