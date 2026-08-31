package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ProblemSolvingEntryVO;
import com.efitops.basesetup.entity.ProblemSolvingRootDetailsVO;

public interface ProblemSolvingRootDetailsRepo extends JpaRepository<ProblemSolvingRootDetailsVO, Long>{

	List<ProblemSolvingRootDetailsVO> findByProblemSolvingEntryVO(ProblemSolvingEntryVO problemSolvingEntryVO);

}
