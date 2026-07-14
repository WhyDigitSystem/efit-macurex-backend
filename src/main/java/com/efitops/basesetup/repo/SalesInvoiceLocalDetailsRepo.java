package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.SalesInvoiceLocalDetailsVO;
import com.efitops.basesetup.entity.SalesInvoiceLocalVO;

public interface SalesInvoiceLocalDetailsRepo extends JpaRepository<SalesInvoiceLocalDetailsVO, Long> {

	List<SalesInvoiceLocalDetailsVO> findBySalesInvoiceLocalVO(SalesInvoiceLocalVO salesInvoiceLocalVO);

}
