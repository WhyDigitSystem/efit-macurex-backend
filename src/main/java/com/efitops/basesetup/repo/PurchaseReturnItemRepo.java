package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseReturnItemVO;
import com.efitops.basesetup.entity.PurchaseReturnVO;
@Repository
public interface PurchaseReturnItemRepo extends JpaRepository<PurchaseReturnItemVO, Long> {

	List<PurchaseReturnItemVO> findByPurchaseReturnVO(PurchaseReturnVO purchaseReturnVO);

}
