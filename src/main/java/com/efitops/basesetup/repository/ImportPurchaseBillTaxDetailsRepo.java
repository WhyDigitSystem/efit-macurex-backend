package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ImportPurchaseBillTaxDetailsVO;
import com.efitops.basesetup.entity.PurchaseBillVO;

@Repository
public interface ImportPurchaseBillTaxDetailsRepo extends JpaRepository<ImportPurchaseBillTaxDetailsVO, Long>{

	List<ImportPurchaseBillTaxDetailsVO> findByPurchaseBillVO(PurchaseBillVO vo);

}
