package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseOrderLocalTaxDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderVO;

@Repository
public interface PurchaseOrderLocalTaxDetailsRepo extends JpaRepository<PurchaseOrderLocalTaxDetailsVO, Long> {

	List<PurchaseOrderLocalTaxDetailsVO> findByPurchaseOrderVO(PurchaseOrderVO vo);

}
