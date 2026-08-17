package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.PurchaseIndentVO;

public interface PurchaseIndentRepo
        extends JpaRepository<PurchaseIndentVO, Long> {

    @Query(value = """
            SELECT *
            FROM Indent_Basic
            WHERE org_id = :orgId
              AND branch = :branch
              AND cancel = false
              AND active = true
            ORDER BY Indent_Basic_id DESC
            """, nativeQuery = true)
    List<PurchaseIndentVO> findByOrgId(
            @Param("orgId") Long orgId,
            @Param("branch") Long branch);

    @Query(value = """
            SELECT COALESCE(MAX(doc_id),0)
            FROM Indent_Basic
            """, nativeQuery = true)
    String findLastIndentNo();

}