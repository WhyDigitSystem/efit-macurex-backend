package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProformaInvoiceTaxDetailsVO;
import com.efitops.basesetup.entity.ProformaInvoiceVO;

@Repository
public interface ProformaInvoiceTaxDetailsRepo extends JpaRepository<ProformaInvoiceTaxDetailsVO, Long> {

	List<ProformaInvoiceTaxDetailsVO> findByProformaInvoiceVO(ProformaInvoiceVO proformaInvoiceVO);

}
