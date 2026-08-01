package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.QuotationItemTaxDetailsVO;
import com.efitops.basesetup.entity.QuotationVO;

@Repository
public interface QuotationItemTaxDetailsRepo extends JpaRepository<QuotationItemTaxDetailsVO, Long> {

	List<QuotationItemTaxDetailsVO> findByQuotationVO(QuotationVO quotationVO);

}
