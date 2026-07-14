package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PaymentInvDtlsVO;
import com.efitops.basesetup.entity.PaymentVO;

@Repository
public interface PaymentInvDtlsRepo extends JpaRepository<PaymentInvDtlsVO, Long>{

	List<PaymentInvDtlsVO> findByPaymentVO(PaymentVO paymentVO);

}
