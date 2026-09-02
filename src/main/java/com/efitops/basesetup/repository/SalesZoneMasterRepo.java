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
              AND cancel = false
            ORDER BY saleszonemaster_id
            """, nativeQuery = true)
    List<SalesZoneMasterVO> findByOrgId(
            @Param("orgId") Long orgId);

    boolean existsByOrgIdAndZoneId(
            Long orgId,
            String zoneId);
}