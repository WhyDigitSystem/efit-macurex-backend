package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DailyPatrolInspectionDetails1VO;
import com.efitops.basesetup.entity.DailyPatrolInspectionVO;
@Repository
public interface DailyPatrolInspectionDetails1Repo extends JpaRepository<DailyPatrolInspectionDetails1VO, Long>{

	List<DailyPatrolInspectionDetails1VO> findByDailyPatrolInspectionVO(
			DailyPatrolInspectionVO dailyPatrolInspectionVO);

}

