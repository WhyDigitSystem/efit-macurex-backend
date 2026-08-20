package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseOrderLocalDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderVO;

@Repository
public interface PurchaseOrderLocalDetailsRepo extends JpaRepository<PurchaseOrderLocalDetailsVO, Long> {

	List<PurchaseOrderLocalDetailsVO> findByPurchaseOrderVO(PurchaseOrderVO vo);

}
