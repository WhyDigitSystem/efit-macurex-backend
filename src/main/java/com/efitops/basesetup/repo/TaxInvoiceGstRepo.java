package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.TaxInvoiceGstVO;
import com.efitops.basesetup.entity.TaxInvoiceVO;

public interface TaxInvoiceGstRepo extends JpaRepository<TaxInvoiceGstVO, Long>{

	List<TaxInvoiceGstVO> findByTaxInvoiceVO(TaxInvoiceVO taxVO);
	

}
