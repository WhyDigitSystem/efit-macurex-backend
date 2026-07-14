package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.GeneralJournalVO;
import com.efitops.basesetup.entity.ParticularsJournalVO;

public interface ParticularsJournalRepo extends JpaRepository<ParticularsJournalVO, Long>{

	List<ParticularsJournalVO> findByGeneralJournalVO(GeneralJournalVO generalJournalVO);


}
