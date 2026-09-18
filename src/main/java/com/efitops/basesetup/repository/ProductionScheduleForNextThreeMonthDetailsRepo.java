package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionScheduleForNextThreeMonthDetailsVO;

@Repository
public interface ProductionScheduleForNextThreeMonthDetailsRepo extends JpaRepository<ProductionScheduleForNextThreeMonthDetailsVO, Long>{

	List<ProductionScheduleForNextThreeMonthDetailsVO> findByProductionScheduleForNextThreeMonthId(Long id);

}
