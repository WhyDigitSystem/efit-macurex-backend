package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.TransportBillPaymentDetailsVO;
import com.efitops.basesetup.entity.TransportBillVO;

public interface TransportBillPaymentDetailsRepo extends JpaRepository<TransportBillPaymentDetailsVO, Long> {

    List<TransportBillPaymentDetailsVO> findByTransportBillVO(TransportBillVO transportBillVO);
}