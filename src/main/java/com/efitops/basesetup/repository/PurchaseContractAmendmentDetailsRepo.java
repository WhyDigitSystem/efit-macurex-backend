package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.PurchaseContractAmendmentDetailsVO;
import com.efitops.basesetup.entity.PurchaseContractAmendmentVO;

public interface PurchaseContractAmendmentDetailsRepo
        extends JpaRepository<PurchaseContractAmendmentDetailsVO, Long> {

    void deleteByPurchaseContractAmendment(
            PurchaseContractAmendmentVO purchaseContractAmendment);

}