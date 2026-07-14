package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DailyPatrolInspectionAttachmentVO;
import com.efitops.basesetup.entity.DailyPatrolInspectionVO;

@Repository
public interface DailyPatrolInspectionAttachmentRepo extends JpaRepository<DailyPatrolInspectionAttachmentVO, Long> {

	List<DailyPatrolInspectionAttachmentVO> findByDailyPatrolInspectionVO(
			DailyPatrolInspectionVO dailyPatrolInspectionVO);

}
