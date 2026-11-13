// PurchaseBillTaxGridRepo.java
package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseBillTaxGridVO;
import com.efitops.basesetup.entity.PurchaseBillVO;

@Repository
public interface PurchaseBillTaxGridRepo extends JpaRepository<PurchaseBillTaxGridVO, Long> {
    List<PurchaseBillTaxGridVO> findByPurchaseBillVO(PurchaseBillVO vo);
}