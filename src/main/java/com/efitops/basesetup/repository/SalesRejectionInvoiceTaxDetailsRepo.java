package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesRejectionInvoiceTaxDetailsVO;
import com.efitops.basesetup.entity.SalesRejectionInvoiceVO;

@Repository
public interface SalesRejectionInvoiceTaxDetailsRepo extends JpaRepository<SalesRejectionInvoiceTaxDetailsVO, Long>{

	List<SalesRejectionInvoiceTaxDetailsVO> findBySalesRejectionInvoiceVO(
			SalesRejectionInvoiceVO salesRejectionInvoiceVO);

}
