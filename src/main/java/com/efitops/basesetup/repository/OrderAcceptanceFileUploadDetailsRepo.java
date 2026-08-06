package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.OrderAcceptanceFileUploadDetailsVO;
import com.efitops.basesetup.entity.OrderAcceptanceVO;

@Repository
public interface OrderAcceptanceFileUploadDetailsRepo extends JpaRepository<OrderAcceptanceFileUploadDetailsVO, Long> {

	List<OrderAcceptanceFileUploadDetailsVO> findByOrderAcceptanceVO(OrderAcceptanceVO orderAcceptanceVO);

}
