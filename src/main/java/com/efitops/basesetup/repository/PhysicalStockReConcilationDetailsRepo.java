package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.PhysicalStockReConcilationDetailsVO;
import com.efitops.basesetup.entity.PhysicalStockReConcilationVO;

public interface PhysicalStockReConcilationDetailsRepo extends JpaRepository<PhysicalStockReConcilationDetailsVO, Long> {

	List<PhysicalStockReConcilationDetailsVO> findByPhysicalStockReConcilationVO(
			PhysicalStockReConcilationVO physicalStockReConcilationVO);

}
