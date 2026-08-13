package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.PurchaseIndentDetailsVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;

public interface PurchaseIndentDetailsRepo extends JpaRepository<PurchaseIndentDetailsVO, Long> {

    List<PurchaseIndentDetailsVO> findByPurchaseIndentVO(PurchaseIndentVO purchaseIndentVO);
}