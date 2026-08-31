package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.InitialPlanningDetailsVO;
import com.efitops.basesetup.entity.InitialPlanningInstrumentDetailsVO;

@Repository
public interface InitialPlanningInstrumentDetailsRepo extends JpaRepository<InitialPlanningInstrumentDetailsVO, Long>{

	List<InitialPlanningInstrumentDetailsVO> findByInitialPlanningDetailsVO(InitialPlanningDetailsVO oldDetail);

}
