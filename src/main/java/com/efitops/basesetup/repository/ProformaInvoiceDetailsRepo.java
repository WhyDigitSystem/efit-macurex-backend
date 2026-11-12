package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProformaInvoiceDetailsVO;
import com.efitops.basesetup.entity.ProformaInvoiceVO;

@Repository
public interface ProformaInvoiceDetailsRepo extends JpaRepository<ProformaInvoiceDetailsVO, Long> {

	List<ProformaInvoiceDetailsVO> findByProformaInvoiceVO(ProformaInvoiceVO proformaInvoiceVO);

}
