package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InprocessInspectionAppearanceVO;
import com.efitops.basesetup.entity.InprocessInspectionVO;

@Repository
public interface InprocessInspectionAppearanceRepo extends JpaRepository<InprocessInspectionAppearanceVO, Long> {

	List<InprocessInspectionAppearanceVO> findByInprocessInspectionVO(InprocessInspectionVO inprocessInspectionVO);

}
