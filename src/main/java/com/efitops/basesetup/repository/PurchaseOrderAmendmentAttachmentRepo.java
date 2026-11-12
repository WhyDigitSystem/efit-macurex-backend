package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.PurchaseOrderAmendmentAttachmentVO;
import com.efitops.basesetup.entity.PurchaseOrderAmendmentVO;

public interface PurchaseOrderAmendmentAttachmentRepo extends JpaRepository<PurchaseOrderAmendmentAttachmentVO, Long> {

	List<PurchaseOrderAmendmentAttachmentVO> findByPurchaseOrderAmendmentVO(PurchaseOrderAmendmentVO vo);

}
