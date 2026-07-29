package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.SalesZoneMasterVO;

public interface SalesZoneMasterRepo extends JpaRepository<SalesZoneMasterVO, Long> {

    @Query(value = """
            SELECT *
            FROM saleszonemaster
            WHERE org_id = :orgId
              AND branch = :branch
              AND cancel = false
              AND active = true
            ORDER BY saleszonemaster_id
            """, nativeQuery = true)
    List<SalesZoneMasterVO> findByOrgIdAndBranch(
            @Param("orgId") Long orgId,
            @Param("branch") Long branch);

    boolean existsByOrgIdAndZoneIdAndBranch_Id(
            Long orgId,
            String zoneId,
            Long branch);
}