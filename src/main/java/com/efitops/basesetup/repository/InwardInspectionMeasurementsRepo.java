package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InwardInspectionMeasurementsVO;

@Repository
public interface InwardInspectionMeasurementsRepo extends JpaRepository<InwardInspectionMeasurementsVO, Long> {

//	List<InwardInspectionMeasurementsVO> findByInwardInspectionDetailsVO(InwardInspectionDetailsVO detailVO);

}
