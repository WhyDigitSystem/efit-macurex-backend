package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesInvoiceExportDetailsVO;
import com.efitops.basesetup.entity.SalesInvoiceExportVO;

@Repository
public interface SalesInvoiceExportDetailsRepo extends JpaRepository<SalesInvoiceExportDetailsVO, Long>{

	List<SalesInvoiceExportDetailsVO> findBySalesInvoiceExportVO(SalesInvoiceExportVO salesInvoiceExportVO);

}
