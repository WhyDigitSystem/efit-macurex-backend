package com.efitops.basesetup.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractQuotationVO;
import com.efitops.basesetup.entity.SubContractorQuotationAttachmentVO;

@Repository
public interface SubContractorQuotationAttachmentRepo extends JpaRepository<SubContractorQuotationAttachmentVO, Long> {

	List<SubContractorQuotationAttachmentVO> findBySubContractQuotationVO(SubContractQuotationVO inprocessInspectionVO);

}
