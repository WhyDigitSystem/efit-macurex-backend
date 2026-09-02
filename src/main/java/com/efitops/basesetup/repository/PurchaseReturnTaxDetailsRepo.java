package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseReturnTaxDetailsVO;
import com.efitops.basesetup.entity.PurchaseReturnVO;

@Repository
public interface PurchaseReturnTaxDetailsRepo extends JpaRepository<PurchaseReturnTaxDetailsVO, Long> {

	List<PurchaseReturnTaxDetailsVO> findByPurchaseReturnVO(PurchaseReturnVO vo);

}
