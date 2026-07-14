package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.QuotationDetailsVO;
import com.efitops.basesetup.entity.QuotationVO;
@Repository
public interface QuotationDetailsRepo extends JpaRepository<QuotationDetailsVO, Long> {

	List<QuotationDetailsVO> findByQuotationVO(QuotationVO quotationVO);

}
