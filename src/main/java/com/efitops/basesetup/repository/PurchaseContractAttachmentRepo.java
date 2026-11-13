package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseContractAttachmentVO;
import com.efitops.basesetup.entity.PurchaseContractVO;

@Repository
public interface PurchaseContractAttachmentRepo extends JpaRepository<PurchaseContractAttachmentVO, Long> {

    List<PurchaseContractAttachmentVO> findByPurchaseContractVO(PurchaseContractVO purchaseContractVO);
}