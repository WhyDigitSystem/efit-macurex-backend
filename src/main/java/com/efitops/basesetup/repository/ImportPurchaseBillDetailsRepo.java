package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ImportPurchaseBillDetailsVO;
import com.efitops.basesetup.entity.PurchaseBillVO;

@Repository
public interface ImportPurchaseBillDetailsRepo extends JpaRepository<ImportPurchaseBillDetailsVO, Long>{

	List<ImportPurchaseBillDetailsVO> findByPurchaseBillVO(PurchaseBillVO vo);

}
