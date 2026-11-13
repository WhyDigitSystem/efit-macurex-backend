package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseContractAmendmentDetailsVO;
import com.efitops.basesetup.entity.PurchaseContractAmendmentVO;

@Repository
public interface PurchaseContractAmendmentDetailsRepo extends JpaRepository<PurchaseContractAmendmentDetailsVO, Long> {
	List<PurchaseContractAmendmentDetailsVO> findByPurchaseContractAmendmentVO(PurchaseContractAmendmentVO vo);

}
