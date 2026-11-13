package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockTransferDetailsVO;
import com.efitops.basesetup.entity.StockTransferVO;

@Repository
public interface StockTransferDetailsRepo extends JpaRepository<StockTransferDetailsVO, Long> {

	List<StockTransferDetailsVO> findByStockTransferVO(StockTransferVO stockTransferVO);

}
