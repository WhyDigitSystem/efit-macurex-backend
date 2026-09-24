package com.efitops.basesetup.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.efitops.basesetup.entity.ProductionOrderDetailsVO;
import com.efitops.basesetup.entity.ProductionSchOrderShortCloseVO;

@Repository
public interface ProductionOrderDetailsRepo extends JpaRepository<ProductionOrderDetailsVO, Long> {
	List<ProductionOrderDetailsVO> findByProductionSchOrderShortCloseVO(
			ProductionSchOrderShortCloseVO productionSchOrderShortCloseVO);
}