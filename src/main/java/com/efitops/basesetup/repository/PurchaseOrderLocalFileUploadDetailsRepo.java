package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseOrderLocalFileUploadDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderVO;

@Repository
public interface PurchaseOrderLocalFileUploadDetailsRepo extends JpaRepository<PurchaseOrderLocalFileUploadDetailsVO, Long> {

	List<PurchaseOrderLocalFileUploadDetailsVO> findByPurchaseOrderVO(PurchaseOrderVO vo);

}
