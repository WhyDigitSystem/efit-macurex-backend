package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DailyPatrolInspectionDocumentsVO;
import com.efitops.basesetup.entity.DailyPatrolInspectionVO;

@Repository
public interface DailyPatrolInspectionDocumentsRepo extends JpaRepository<DailyPatrolInspectionDocumentsVO, Long>{

	Iterable<? extends DailyPatrolInspectionDocumentsVO> findByDailyPatrolInspectionVO(
			DailyPatrolInspectionVO dailyPatrolInspectionVO);

}
