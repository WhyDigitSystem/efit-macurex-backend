package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionScheduleOrderVO;
import com.efitops.basesetup.entity.ScheduleDetailsVO;

@Repository
public interface ScheduleDetailsRepo extends JpaRepository<ScheduleDetailsVO, Long> {

	List<ScheduleDetailsVO> findByProductionScheduleOrderVO(ProductionScheduleOrderVO vo);

}
