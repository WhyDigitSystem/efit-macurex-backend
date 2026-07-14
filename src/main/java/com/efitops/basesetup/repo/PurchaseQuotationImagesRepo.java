package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseQuotationImagesVO;
import com.efitops.basesetup.entity.PurchaseQuotationVO;

@Repository
public interface PurchaseQuotationImagesRepo extends JpaRepository<PurchaseQuotationImagesVO, Long> {

	List<PurchaseQuotationImagesVO> findByPurchaseQuotationVO(PurchaseQuotationVO inspection);
}
