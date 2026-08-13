package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.PurchaseIndentAttachmentVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;

public interface PurchaseIndentAttachmentRepo extends JpaRepository<PurchaseIndentAttachmentVO, Long> {

    List<PurchaseIndentAttachmentVO> findByPurchaseIndentVO(PurchaseIndentVO purchaseIndentVO);
}