package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ParticularsPaymentVoucherVO;
import com.efitops.basesetup.entity.PaymentVoucherVO;

public interface ParticularsPaymentVoucherRepo extends JpaRepository<ParticularsPaymentVoucherVO, Long> {

	List<ParticularsPaymentVoucherVO> findByPaymentVoucherVO(PaymentVoucherVO paymentVoucherVO);

}
