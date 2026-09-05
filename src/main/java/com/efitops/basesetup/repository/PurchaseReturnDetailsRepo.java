package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PurchaseReturnDetailsVO;
import com.efitops.basesetup.entity.PurchaseReturnVO;

@Repository
public interface PurchaseReturnDetailsRepo extends JpaRepository<PurchaseReturnDetailsVO, Long> {

	List<PurchaseReturnDetailsVO> findByPurchaseReturnVO(PurchaseReturnVO vo);

}
