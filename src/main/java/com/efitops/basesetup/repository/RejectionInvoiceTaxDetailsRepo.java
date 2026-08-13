package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RejectionInvoiceTaxDetailsVO;
import com.efitops.basesetup.entity.RejectionInvoiceVO;

@Repository
public interface RejectionInvoiceTaxDetailsRepo extends JpaRepository<RejectionInvoiceTaxDetailsVO, Long> {

	List<RejectionInvoiceTaxDetailsVO> findByRejectionInvoiceVO(RejectionInvoiceVO rejectionInvoiceVO);

}
