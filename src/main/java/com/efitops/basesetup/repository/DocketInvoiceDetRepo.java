package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.DocketInvoiceDetailsVO;
import com.efitops.basesetup.entity.DocketInvoiceVO;

public interface DocketInvoiceDetRepo extends JpaRepository<DocketInvoiceDetailsVO, Long>{

	List<DocketInvoiceDetailsVO> findByDocketInvoiceVO(DocketInvoiceVO docketInvoiceVO);

}
