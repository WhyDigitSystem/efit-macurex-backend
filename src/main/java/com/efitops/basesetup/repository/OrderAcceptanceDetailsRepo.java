package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.OrderAcceptanceDetailsVO;
import com.efitops.basesetup.entity.OrderAcceptanceVO;

@Repository
public interface OrderAcceptanceDetailsRepo extends JpaRepository<OrderAcceptanceDetailsVO, Long> {

	List<OrderAcceptanceDetailsVO> findByOrderAcceptanceVO(OrderAcceptanceVO orderAcceptanceVO);

}
