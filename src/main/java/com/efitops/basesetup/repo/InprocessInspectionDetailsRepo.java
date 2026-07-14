package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InprocessInspectionDetailsVO;
import com.efitops.basesetup.entity.InprocessInspectionVO;

@Repository
public interface InprocessInspectionDetailsRepo extends JpaRepository<InprocessInspectionDetailsVO, Long> {

	List<InprocessInspectionDetailsVO> findByInprocessInspectionVO(InprocessInspectionVO inprocessInspectionVO);

}
