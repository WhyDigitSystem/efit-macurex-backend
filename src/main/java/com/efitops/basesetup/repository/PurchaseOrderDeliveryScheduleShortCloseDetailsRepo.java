package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseOrderDeliveryScheduleShortCloseDetailsVO;
import com.efitops.basesetup.entity.PurchaseOrderDeliveryScheduleShortCloseVO;

@Repository
public interface PurchaseOrderDeliveryScheduleShortCloseDetailsRepo
		extends JpaRepository<PurchaseOrderDeliveryScheduleShortCloseDetailsVO, Long> {

	List<PurchaseOrderDeliveryScheduleShortCloseDetailsVO> findByPurchaseOrderDeliveryScheduleShortCloseVO(
			PurchaseOrderDeliveryScheduleShortCloseVO purchaseOrderDeliveryScheduleShortCloseVO);

}
