package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.PurchaseContractAmendmentAttachmentVO;
import com.efitops.basesetup.entity.PurchaseContractAmendmentVO;

public interface PurchaseContractAmendmentAttachmentRepo
        extends JpaRepository<PurchaseContractAmendmentAttachmentVO, Long> {

    List<PurchaseContractAmendmentAttachmentVO>
            findByPurchaseContractAmendmentVO(
                    PurchaseContractAmendmentVO purchaseContractAmendmentVO);

    void deleteByPurchaseContractAmendmentVO(
            PurchaseContractAmendmentVO purchaseContractAmendmentVO);
}