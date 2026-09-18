package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ImportPurchaseBillChargesSummaryVO;
import com.efitops.basesetup.entity.PurchaseBillVO;

@Repository
public interface ImportPurchaseBillChargesSummaryRepo extends JpaRepository<ImportPurchaseBillChargesSummaryVO,Long>{

	List<ImportPurchaseBillChargesSummaryVO> findByPurchaseBillVO(PurchaseBillVO vo);


}
