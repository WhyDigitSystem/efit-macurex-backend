package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.QuotationItemDetailsVO;
import com.efitops.basesetup.entity.QuotationVO;

@Repository
public interface QuotationItemDetailsRepo extends JpaRepository<QuotationItemDetailsVO, Long> {

	List<QuotationItemDetailsVO> findByQuotationVO(QuotationVO quotationVO);

}
