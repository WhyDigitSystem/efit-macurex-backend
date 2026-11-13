package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SalesRejectionInvoiceDetailsVO;
import com.efitops.basesetup.entity.SalesRejectionInvoiceVO;

@Repository
public interface SalesRejectionInvoiceDetailsRepo extends JpaRepository<SalesRejectionInvoiceDetailsVO, Long>{

	List<SalesRejectionInvoiceDetailsVO> findBySalesRejectionInvoiceVO(SalesRejectionInvoiceVO salesRejectionInvoiceVO);

}
