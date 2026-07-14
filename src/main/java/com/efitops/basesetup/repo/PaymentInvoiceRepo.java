package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.PaymentInvoiceVO;

public interface PaymentInvoiceRepo extends JpaRepository<PaymentInvoiceVO, Long> {

}
