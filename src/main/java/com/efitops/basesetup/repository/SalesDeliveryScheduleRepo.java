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
              AND branch_id = :branchId
              AND cancel = false
              AND active = true
            ORDER BY sales_delivery_schedule_id
            """, nativeQuery = true)
    List<SalesDeliveryScheduleVO> findByOrgIdAndBranch(
            @Param("orgId") Long orgId,
            @Param("branchId") Long branchId);

    boolean existsByDlvNoAndOrgIdAndBranch_Id(
            String dlvNo,
            Long orgId,
            Long branchId);

}