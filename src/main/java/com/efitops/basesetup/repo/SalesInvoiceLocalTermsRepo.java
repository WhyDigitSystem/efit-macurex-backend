package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.SalesInvoiceLocalTermsVO;

public interface SalesInvoiceLocalTermsRepo extends JpaRepository<SalesInvoiceLocalTermsVO, Long> {
//
//	List<SalesInvoiceLocalTermsVO> findBySalesInvoiceLocalVO(SalesInvoiceLocalVO salesInvoiceLocalVO);

}
