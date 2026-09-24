package com.efitops.basesetup.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.efitops.basesetup.entity.ScrapNoteDetailsVO;
import com.efitops.basesetup.entity.ScrapNoteVO;

@Repository
public interface ScrapNoteDetailsRepo extends JpaRepository<ScrapNoteDetailsVO, Long> {
    List<ScrapNoteDetailsVO> findByScrapNoteVO(ScrapNoteVO scrapNoteVO);
}
