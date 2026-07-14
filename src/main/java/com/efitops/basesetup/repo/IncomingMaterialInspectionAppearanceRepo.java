package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.IncomingMaterialInspectionAppearanceVO;
import com.efitops.basesetup.entity.IncomingMaterialInspectionVO;

@Repository

public interface IncomingMaterialInspectionAppearanceRepo extends JpaRepository<IncomingMaterialInspectionAppearanceVO, Long> {

	List<IncomingMaterialInspectionAppearanceVO> findByIncomingMaterialInspectionVO(IncomingMaterialInspectionVO incomingMaterialInspectionVO);

}
