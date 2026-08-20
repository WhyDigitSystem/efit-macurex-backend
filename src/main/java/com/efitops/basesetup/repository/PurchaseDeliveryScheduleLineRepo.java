package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseDeliveryScheduleDetailsVO;
import com.efitops.basesetup.entity.PurchaseDeliveryScheduleLineVO;
import com.efitops.basesetup.entity.PurchaseDeliveryScheduleVO;

@Repository
public interface PurchaseDeliveryScheduleLineRepo extends JpaRepository<PurchaseDeliveryScheduleLineVO, Long> {

	List<PurchaseDeliveryScheduleLineVO> findByPurchaseDeliveryScheduleDetailsVO(
			PurchaseDeliveryScheduleDetailsVO purchaseDeliveryScheduleDetailsVO);
}
