package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractInvoiceTermsVO;
import com.efitops.basesetup.entity.SubContractInvoiceVO;

@Repository
public interface SubContractInvoiceTermsRepo extends JpaRepository<SubContractInvoiceTermsVO, Long> {

	List<SubContractInvoiceTermsVO> findBySubContractInvoiceVO(SubContractInvoiceVO subContractInvoiceVO);

}
