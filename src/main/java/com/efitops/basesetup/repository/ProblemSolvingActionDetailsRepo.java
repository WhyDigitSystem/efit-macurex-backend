package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ProblemSolvingActionDetailsVO;
import com.efitops.basesetup.entity.ProblemSolvingEntryVO;

public interface ProblemSolvingActionDetailsRepo extends JpaRepository<ProblemSolvingActionDetailsVO, Long>{

	List<ProblemSolvingActionDetailsVO> findByProblemSolvingEntryVO(ProblemSolvingEntryVO problemSolvingEntryVO);

}
