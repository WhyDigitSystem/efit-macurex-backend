package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.OtherSalesInvoiceDetailsVO;
import com.efitops.basesetup.entity.OtherSalesInvoiceVO;

@Repository
public interface OtherSalesInvoiceDetailsRepo extends JpaRepository<OtherSalesInvoiceDetailsVO, Long> {

	List<OtherSalesInvoiceDetailsVO> findByOtherSalesInvoiceVO(OtherSalesInvoiceVO otherSalesInvoiceVO);

}
