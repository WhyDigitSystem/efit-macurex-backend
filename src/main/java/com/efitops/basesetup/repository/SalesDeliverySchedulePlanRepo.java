package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.SalesDeliverySchedulePlanVO;

public interface SalesDeliverySchedulePlanRepo
        extends JpaRepository<SalesDeliverySchedulePlanVO, Long> {

}