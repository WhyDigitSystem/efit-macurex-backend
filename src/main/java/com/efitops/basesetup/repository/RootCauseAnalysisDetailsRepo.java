package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.RootCauseAnalysisDetailsVO;
import com.efitops.basesetup.entity.RootCauseAnalysisVO;

public interface RootCauseAnalysisDetailsRepo extends JpaRepository<RootCauseAnalysisDetailsVO, Long> {

	List<RootCauseAnalysisDetailsVO> findByRootCauseAnalysisVO(RootCauseAnalysisVO rootCauseAnalysisVO);

}
