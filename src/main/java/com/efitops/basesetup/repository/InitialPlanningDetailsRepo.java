package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.InitialPlanningDetailsVO;
import com.efitops.basesetup.entity.InitialPlanningVO;

public interface InitialPlanningDetailsRepo extends JpaRepository<InitialPlanningDetailsVO, Long> {

	List<InitialPlanningDetailsVO> findByInitialPlanningVO(InitialPlanningVO initialPlanningVO);

}
