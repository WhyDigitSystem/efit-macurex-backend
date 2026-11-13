package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DirectPurchaseCashDetailsVO;
import com.efitops.basesetup.entity.DirectPurchaseVO;

@Repository
public interface DirectPurchaseCashDetailsRepo extends JpaRepository<DirectPurchaseCashDetailsVO, Long> {

	List<DirectPurchaseCashDetailsVO> findByDirectPurchaseVO(DirectPurchaseVO vo);

}
