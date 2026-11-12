package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseContractTaxDetailsVO;
import com.efitops.basesetup.entity.PurchaseContractVO;

@Repository
public interface PurchaseContractTaxDetailsRepo extends JpaRepository<PurchaseContractTaxDetailsVO, Long> {

    List<PurchaseContractTaxDetailsVO> findByPurchaseContractVO(PurchaseContractVO purchaseContractVO);
}