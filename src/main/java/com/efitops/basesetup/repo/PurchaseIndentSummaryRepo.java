package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseIndentSummaryVO;
import com.efitops.basesetup.entity.PurchaseIndentVO;
@Repository
public interface PurchaseIndentSummaryRepo extends JpaRepository<PurchaseIndentSummaryVO, Long>{

	List<PurchaseIndentSummaryVO> findByPurchaseIndentVO(PurchaseIndentVO purchaseIndentVO);

}
