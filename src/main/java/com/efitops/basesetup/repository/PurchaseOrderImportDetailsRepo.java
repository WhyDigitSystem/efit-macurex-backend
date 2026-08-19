package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseOrderImportDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderVO;

@Repository
public interface PurchaseOrderImportDetailsRepo extends JpaRepository<PurchaseOrderImportDetailsVO, Long> {

	List<PurchaseOrderImportDetailsVO> findByPurchaseOrderVO(PurchaseOrderVO vo);

}
