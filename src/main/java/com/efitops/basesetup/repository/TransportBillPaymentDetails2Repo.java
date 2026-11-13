package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.TransportBillPaymentDetails2VO;
import com.efitops.basesetup.entity.TransportBillVO;

public interface TransportBillPaymentDetails2Repo extends JpaRepository<TransportBillPaymentDetails2VO, Long> {

//    List<TransportBillPaymentDetails2VO> findByTransportBillVO(TransportBillVO transportBillVO);
}