package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseQuotationDetailsVO;
import com.efitops.basesetup.entity.PurchaseQuotationVO;

@Repository
public interface PurchaseQuotationDetailsRepo extends JpaRepository<PurchaseQuotationDetailsVO, Long>{

	List<PurchaseQuotationDetailsVO> findByPurchaseQuotationVO(PurchaseQuotationVO purchaseQuotationVO);


}
