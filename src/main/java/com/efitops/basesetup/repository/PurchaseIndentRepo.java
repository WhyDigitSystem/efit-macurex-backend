package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseIndentVO;

@Repository
public interface PurchaseIndentRepo extends JpaRepository<PurchaseIndentVO, Long> {

    Optional<PurchaseIndentVO> findById(Long id);

    @Query(nativeQuery = true,
            value = "select * from purchaseindent where org_id=?1 and branch=?2")
    List<PurchaseIndentVO> findByOrgIdAndBranch(Long orgId, Long branchId);

    long countByOrgId(Long orgId);

    // Duplicate check on create
    boolean existsByIndentNoAndOrgId(String indentNo, Long orgId);

    // Duplicate check on update
    boolean existsByIndentNoAndOrgIdAndIdNot(
            String indentNo,
            Long orgId,
            Long id);

    // Fallback lookup
    Optional<PurchaseIndentVO> findByIndentNoAndOrgId(
            String indentNo,
            Long orgId);
}