package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.PurchaseIndentVO;

public interface PurchaseIndentRepo extends JpaRepository<PurchaseIndentVO, Long> {

    Optional<PurchaseIndentVO> findById(Long id);

    List<PurchaseIndentVO> findByOrgIdAndPlant_Id(Long orgId, Long branchId);

    long countByOrgId(Long orgId);

    // Duplicate check on create: does this indentNo already exist for the org?
    boolean existsByIndentNoAndOrgId(String indentNo, Long orgId);

    // Duplicate check on update: does this indentNo already exist for the org,
    // on some OTHER record (excludes the record being updated itself).
    boolean existsByIndentNoAndOrgIdAndIdNot(String indentNo, Long orgId, Long id);

    // Fallback lookup: find an existing record by indentNo + org, used when
    // the caller's id is missing/0 but indentNo matches an existing record -
    // routes the request into the update path instead of creating a duplicate.
    Optional<PurchaseIndentVO> findByIndentNoAndOrgId(String indentNo, Long orgId);
}