package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseContractDetailsVO;
import com.efitops.basesetup.entity.PurchaseContractVO;

@Repository
public interface PurchaseContractDetailsRepo extends JpaRepository<PurchaseContractDetailsVO, Long> {

    List<PurchaseContractDetailsVO> findByPurchaseContractVO(PurchaseContractVO purchaseContractVO);
}