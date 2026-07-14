package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseDetailsVO;

@Repository
public interface PurchaseDetailsRepo extends JpaRepository<PurchaseDetailsVO, Long> {

}
