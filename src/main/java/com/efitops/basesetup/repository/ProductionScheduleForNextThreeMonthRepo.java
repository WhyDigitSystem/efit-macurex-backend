package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProductionScheduleForNextThreeMonthVO;

@Repository
public interface ProductionScheduleForNextThreeMonthRepo
		extends JpaRepository<ProductionScheduleForNextThreeMonthVO, Long> {

	@Query(value = "SELECT * FROM production_schedule_for_next_three_month " + "WHERE org_id = :orgId "
			+ "AND branch = :branch", nativeQuery = true)
	List<ProductionScheduleForNextThreeMonthVO> findByOrgIdAndBranchId(@Param("orgId") Long orgId,
			@Param("branch") Long branch);
}
