// LocalPurchaseOrderTaxDetailsRepo.java
package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.LocalPurchaseOrderTaxDetailsVO;

@Repository
public interface LocalPurchaseOrderTaxDetailsRepo extends JpaRepository<LocalPurchaseOrderTaxDetailsVO, Long> {
}