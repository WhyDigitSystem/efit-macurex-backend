package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.SalesDeliveryScheduleVO;

public interface SalesDeliveryScheduleRepo extends JpaRepository<SalesDeliveryScheduleVO, Long> {

	@Query(value = """
	        SELECT *
	        FROM sdvbasic
	        WHERE org_id = :orgId
	          AND branch_id = :branch
	          AND cancel = 0
	          AND active = 1
	        ORDER BY sdvbasic_id
	        """, nativeQuery = true)
	List<SalesDeliveryScheduleVO> findByOrgIdAndBranch(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);

    boolean existsByDlvNoAndOrgIdAndBranch_Id(
            String dlvNo,
            Long orgId,
            Long branchId);

}