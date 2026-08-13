// DirectPurchaseDetailsRepo.java
package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DirectPurchaseDetailsVO;
import com.efitops.basesetup.entity.DirectPurchaseVO;

@Repository
public interface DirectPurchaseDetailsRepo extends JpaRepository<DirectPurchaseDetailsVO, Long> {
    List<DirectPurchaseDetailsVO> findByDirectPurchaseVO(DirectPurchaseVO vo);
}