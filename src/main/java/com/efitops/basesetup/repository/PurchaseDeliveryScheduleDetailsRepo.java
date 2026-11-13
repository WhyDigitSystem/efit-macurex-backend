package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseDeliveryScheduleDetailsVO;
import com.efitops.basesetup.entity.PurchaseDeliveryScheduleVO;

@Repository
public interface PurchaseDeliveryScheduleDetailsRepo extends JpaRepository<PurchaseDeliveryScheduleDetailsVO, Long> {

    List<PurchaseDeliveryScheduleDetailsVO> findByPurchaseDeliveryScheduleVO(PurchaseDeliveryScheduleVO vo);
}