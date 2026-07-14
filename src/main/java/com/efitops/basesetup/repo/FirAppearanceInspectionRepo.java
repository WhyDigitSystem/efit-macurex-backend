package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.FinalInspectionReportVO;
import com.efitops.basesetup.entity.FirAppearanceInspectionVO;

@Repository
public interface FirAppearanceInspectionRepo extends JpaRepository<FirAppearanceInspectionVO, Long> {

	List<FirAppearanceInspectionVO> findByFinalInspectionReportVO(FinalInspectionReportVO finalInspectionReportVO);

}
