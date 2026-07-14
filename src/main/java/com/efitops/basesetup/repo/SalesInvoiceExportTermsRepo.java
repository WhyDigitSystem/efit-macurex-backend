package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesInvoiceExportTermsVO;
import com.efitops.basesetup.entity.SalesInvoiceExportVO;

@Repository
public interface SalesInvoiceExportTermsRepo extends JpaRepository<SalesInvoiceExportTermsVO, Long> {

	List<SalesInvoiceExportTermsVO> findBySalesInvoiceExportVO(SalesInvoiceExportVO salesInvoiceExportVO);

}
