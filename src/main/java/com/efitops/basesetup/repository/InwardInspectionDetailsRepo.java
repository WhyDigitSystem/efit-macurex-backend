package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InwardInspectionDetailsVO;
import com.efitops.basesetup.entity.InwardInspectionVO;

@Repository
public interface InwardInspectionDetailsRepo  extends JpaRepository<InwardInspectionDetailsVO, Long>{

	List<InwardInspectionDetailsVO> findByInwardInspectionVO(InwardInspectionVO vo);

//	List<InwardInspectionDetailsVO> findByInwardInspectionVO(InwardInspectionVO vo);

}
