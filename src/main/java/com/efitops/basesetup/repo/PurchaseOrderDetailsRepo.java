package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseOrderDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderVO;

@Repository
public interface PurchaseOrderDetailsRepo extends JpaRepository<PurchaseOrderDetailsVO	, Long>{

	
	List<PurchaseOrderDetailsVO> findByPurchaseOrderVO(PurchaseOrderVO purchaseOrderVO);

	

	
}
