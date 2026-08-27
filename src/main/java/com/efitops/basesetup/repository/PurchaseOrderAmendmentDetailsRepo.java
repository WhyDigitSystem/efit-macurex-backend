package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.PurchaseIndentDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderAmendmentDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderAmendmentVO;

public interface PurchaseOrderAmendmentDetailsRepo extends JpaRepository<PurchaseOrderAmendmentDetailsVO, Long> {

	List<PurchaseOrderAmendmentDetailsVO> findByPurchaseOrderAmendmentVO(PurchaseOrderAmendmentVO vo);
	

}
