// PurchaseShortCloseDetailsRepo.java
package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseShortCloseDetailsVO;

@Repository
public interface PurchaseShortCloseDetailsRepo extends JpaRepository<PurchaseShortCloseDetailsVO, Long> {
}