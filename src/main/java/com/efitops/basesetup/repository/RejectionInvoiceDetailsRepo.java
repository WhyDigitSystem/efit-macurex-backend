package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RejectionInvoiceDetailsVO;
import com.efitops.basesetup.entity.RejectionInvoiceVO;
@Repository
public interface RejectionInvoiceDetailsRepo extends JpaRepository<RejectionInvoiceDetailsVO, Long> {

	List<RejectionInvoiceDetailsVO> findByRejectionInvoiceVO(RejectionInvoiceVO rejectionInvoiceVO);

}
