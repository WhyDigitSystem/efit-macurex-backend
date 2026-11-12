package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ProblemSolvingEntryVO;
import com.efitops.basesetup.entity.ProblemSolvingOtherDetailsVO;

public interface ProblemSolvingOtherDetailsRepo extends JpaRepository<ProblemSolvingOtherDetailsVO, Long> {

	List<ProblemSolvingOtherDetailsVO> findByProblemSolvingEntryVO(ProblemSolvingEntryVO problemSolvingEntryVO);

}
