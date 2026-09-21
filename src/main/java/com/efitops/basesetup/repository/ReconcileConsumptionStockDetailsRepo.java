package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ReconcileConsumptionStockDetailsVO;
import com.efitops.basesetup.entity.ReconcileConsumptionStockVO;

@Repository
public interface ReconcileConsumptionStockDetailsRepo  extends JpaRepository<ReconcileConsumptionStockDetailsVO, Long>{

	List<ReconcileConsumptionStockDetailsVO> findByReconcileConsumptionStockVO(
			ReconcileConsumptionStockVO reconcileConsumptionStockVO);

}
