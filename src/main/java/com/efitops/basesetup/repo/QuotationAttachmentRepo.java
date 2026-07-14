package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.QuotationAttachmentVO;
import com.efitops.basesetup.entity.QuotationVO;

public interface QuotationAttachmentRepo extends JpaRepository<QuotationAttachmentVO, Long>{


	List<QuotationAttachmentVO> findByQuotationVO(QuotationVO quotationVO);

}
