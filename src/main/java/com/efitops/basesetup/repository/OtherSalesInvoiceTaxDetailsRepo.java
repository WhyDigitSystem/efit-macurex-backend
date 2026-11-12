package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.OtherSalesInvoiceTaxDetailsVO;
import com.efitops.basesetup.entity.OtherSalesInvoiceVO;

@Repository
public interface OtherSalesInvoiceTaxDetailsRepo extends JpaRepository<OtherSalesInvoiceTaxDetailsVO, Long> {

	List<OtherSalesInvoiceTaxDetailsVO> findByOtherSalesInvoiceVO(OtherSalesInvoiceVO otherSalesInvoiceVO);

}
