package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.SalesReturnVO;

public interface SalesReturnRepo extends JpaRepository<SalesReturnVO, Long> {

    @Query(value = """
            SELECT *
            FROM sales_return
            WHERE org_id = :orgId
              AND branch_id = :branchId
              AND cancel = false
              AND active = true
            ORDER BY sales_return_id DESC
            """, nativeQuery = true)
    List<SalesReturnVO> findByOrgIdAndBranch(
            @Param("orgId") Long orgId,
            @Param("branchId") Long branchId);

    boolean existsByDocNoAndOrgIdAndBranch_Id(
            String docNo,
            Long orgId,
            Long branchId);

}