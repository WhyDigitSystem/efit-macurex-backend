package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DailyPatrolInspectionFinalVO;
import com.efitops.basesetup.entity.DailyPatrolInspectionVO;

@Repository
public interface DailyPatrolInspectionFinalRepo extends JpaRepository<DailyPatrolInspectionFinalVO, Long>{

	List<DailyPatrolInspectionFinalVO> findByDailyPatrolInspectionVO(DailyPatrolInspectionVO dailyPatrolInspectionVO);


}
