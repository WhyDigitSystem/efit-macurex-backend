package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.SalesDeliveryScheduleDetailsVO;

public interface SalesDeliveryScheduleDetailsRepo
        extends JpaRepository<SalesDeliveryScheduleDetailsVO, Long> {

    List<SalesDeliveryScheduleDetailsVO> findBySalesDeliveryScheduleId(Long salesDeliveryScheduleId);

}