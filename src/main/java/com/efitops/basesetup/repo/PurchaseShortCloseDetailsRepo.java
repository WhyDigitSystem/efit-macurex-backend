package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseShortCloseDetailsVO;
import com.efitops.basesetup.entity.PurchaseShortCloseVO;

@Repository
public interface PurchaseShortCloseDetailsRepo extends JpaRepository<PurchaseShortCloseDetailsVO, Long> {

	List<PurchaseShortCloseDetailsVO> findByPurchaseShortCloseVO(PurchaseShortCloseVO purchaseShortCloseVO);

}
