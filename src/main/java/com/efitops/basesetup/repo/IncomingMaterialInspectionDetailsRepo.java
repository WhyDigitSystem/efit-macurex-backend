package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.IncomingMaterialInspectionDetailsVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;

@Repository

public interface IncomingMaterialInspectionDetailsRepo extends JpaRepository<IncomingMaterialInspectionDetailsVO, Long> {

	List<IncomingMaterialInspectionDetailsVO> findByIncomingMaterialInspectionVO(IncomingMaterialInspectionVO incomingMaterialInspectionVO);

}
