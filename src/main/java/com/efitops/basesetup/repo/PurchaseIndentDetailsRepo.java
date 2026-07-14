package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseIndentDetailsVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;

@Repository
public interface PurchaseIndentDetailsRepo extends JpaRepository<PurchaseIndentDetailsVO, Long>{

	List<PurchaseIndentDetailsVO> findByPurchaseIndentVO(PurchaseIndentVO purchaseIndentVO);
	
}
