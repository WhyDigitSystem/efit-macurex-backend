package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DirectPurchaseFileUploadDetailsVO;
import com.efitops.basesetup.entity.DirectPurchaseVO;

@Repository
public interface DirectPurchaseFileUploadDetailsRepo extends JpaRepository<DirectPurchaseFileUploadDetailsVO, Long> {

	List<DirectPurchaseFileUploadDetailsVO> findByDirectPurchaseVO(DirectPurchaseVO directPurchaseVO);

}
