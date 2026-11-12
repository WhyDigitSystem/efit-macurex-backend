package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.QuotationIemFileUploadDetailsVO;
import com.efitops.basesetup.entity.QuotationVO;

@Repository
public interface QuotationIemFileUploadDetailsRepo extends JpaRepository<QuotationIemFileUploadDetailsVO, Long> {

	List<QuotationIemFileUploadDetailsVO> findByQuotationVO(QuotationVO quotationVO);

}
