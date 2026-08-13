// DirectPurchaseTaxDetailsRepo.java
package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DirectPurchaseTaxDetailsVO;
import com.efitops.basesetup.entity.DirectPurchaseVO;

@Repository
public interface DirectPurchaseTaxDetailsRepo extends JpaRepository<DirectPurchaseTaxDetailsVO, Long> {
    List<DirectPurchaseTaxDetailsVO> findByDirectPurchaseVO(DirectPurchaseVO vo);
}