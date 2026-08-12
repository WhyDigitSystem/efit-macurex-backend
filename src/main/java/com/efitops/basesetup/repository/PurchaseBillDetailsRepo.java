// PurchaseBillDetailsRepo.java
package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseBillDetailsVO;
import com.efitops.basesetup.entity.PurchaseBillVO;

@Repository
public interface PurchaseBillDetailsRepo extends JpaRepository<PurchaseBillDetailsVO, Long> {
    List<PurchaseBillDetailsVO> findByPurchaseBillVO(PurchaseBillVO vo);
}