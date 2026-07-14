package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractInvoiceDetailsVO;
import com.efitops.basesetup.entity.SubContractInvoiceVO;

@Repository
public interface SubContractInvoiceDetailsRepo extends JpaRepository<SubContractInvoiceDetailsVO, Long> {

	List<SubContractInvoiceDetailsVO> findBySubContractInvoiceVO(SubContractInvoiceVO subContractInvoiceVO);

}
