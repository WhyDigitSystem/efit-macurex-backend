package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.PurchaseContractAmendmentDetailsVO;
import com.efitops.basesetup.entity.PurchaseContractAmendmentVO;

public interface PurchaseContractAmendmentDetailsRepo
        extends JpaRepository<PurchaseContractAmendmentDetailsVO, Long> {

	void deleteByPurchaseContractAmendmentVO(PurchaseContractAmendmentVO vo);

	List<PurchaseContractAmendmentDetailsVO> findByPurchaseContractAmendmentVO(PurchaseContractAmendmentVO vo);
	

    
}

