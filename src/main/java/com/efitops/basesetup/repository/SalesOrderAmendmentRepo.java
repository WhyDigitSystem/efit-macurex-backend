package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.SalesOrderAmendmentVO;

public interface SalesOrderAmendmentRepo extends JpaRepository<SalesOrderAmendmentVO, Long> {

    @Query(value = """
            SELECT *
            FROM salesorderamendment
            WHERE org_id = :orgId
              AND branch = :branch
              AND cancel = false
              AND active = true
            ORDER BY id DESC
            """, nativeQuery = true)
    List<SalesOrderAmendmentVO> findByOrgId(@Param("orgId") Long orgId,
                                            @Param("branch") Long branch);

    @Query(value = """
            SELECT COALESCE(MAX(revision_no), 0)
            FROM salesorderamendment
            WHERE s_o_no = :soNumber
              AND cancel = false
            """, nativeQuery = true)
    Integer findMaxRevisionNo(@Param("soNumber") String soNumber);

}