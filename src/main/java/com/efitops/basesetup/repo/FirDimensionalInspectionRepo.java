package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.FinalInspectionReportVO;
import com.efitops.basesetup.entity.FirDimensionalInspectionVO;

@Repository
public interface FirDimensionalInspectionRepo extends JpaRepository<FirDimensionalInspectionVO, Long> {

	List<FirDimensionalInspectionVO> findByFinalInspectionReportVO(FinalInspectionReportVO finalInspectionReportVO);

}
