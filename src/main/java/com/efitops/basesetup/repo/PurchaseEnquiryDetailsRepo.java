package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseEnquiryDetailsVO;
import com.efitops.basesetup.entity.PurchaseEnquiryVO;

@Repository
public interface PurchaseEnquiryDetailsRepo extends JpaRepository<PurchaseEnquiryDetailsVO, Long> {

	List<PurchaseEnquiryDetailsVO> findByPurchaseEnquiryVO(PurchaseEnquiryVO purchaseEnquiryVO);

}
