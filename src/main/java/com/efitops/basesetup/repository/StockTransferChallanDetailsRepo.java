package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockTransferChallanDetailsVO;
import com.efitops.basesetup.entity.StockTransferChallanVO;

@Repository
public interface StockTransferChallanDetailsRepo extends JpaRepository<StockTransferChallanDetailsVO, Long> {

	List<StockTransferChallanDetailsVO> findByStockTransferChallanVO(StockTransferChallanVO stockTransferChallanVO);

}
