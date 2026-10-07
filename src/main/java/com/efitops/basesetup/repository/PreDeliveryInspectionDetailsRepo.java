package com.efitops.basesetup.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PreDeliveryInspectionDetailsVO;
import com.efitops.basesetup.entity.PreDeliveryInspectionVO;

@Repository
public interface PreDeliveryInspectionDetailsRepo extends JpaRepository<PreDeliveryInspectionDetailsVO, Long> {
    
    List<PreDeliveryInspectionDetailsVO> findByPreDeliveryInspectionVO(PreDeliveryInspectionVO preDeliveryInspectionVO);
}