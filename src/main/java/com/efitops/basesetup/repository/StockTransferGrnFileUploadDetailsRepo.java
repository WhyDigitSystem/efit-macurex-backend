package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockTransferGrnFileUploadDetailsVO;
import com.efitops.basesetup.entity.StockTransferGrnVO;

@Repository
public interface StockTransferGrnFileUploadDetailsRepo
		extends JpaRepository<StockTransferGrnFileUploadDetailsVO, Long> {

	List<StockTransferGrnFileUploadDetailsVO> findByStockTransferGrnVO(StockTransferGrnVO vo);

}
