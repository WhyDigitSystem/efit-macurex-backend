package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseBillChargesSummaryVO;
import com.efitops.basesetup.entity.PurchaseBillVO;

@Repository 
public interface PurchaseBillChargesSummaryRepo extends JpaRepository<PurchaseBillChargesSummaryVO, Long>{

	List<PurchaseBillChargesSummaryVO> findByPurchaseBillVO(PurchaseBillVO vo);

}
