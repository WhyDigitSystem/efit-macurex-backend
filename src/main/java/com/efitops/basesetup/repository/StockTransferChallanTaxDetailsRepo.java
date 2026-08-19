package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockTransferChallanTaxDetailsVO;
import com.efitops.basesetup.entity.StockTransferChallanVO;

@Repository
public interface StockTransferChallanTaxDetailsRepo extends JpaRepository<StockTransferChallanTaxDetailsVO, Long>{

	List<StockTransferChallanTaxDetailsVO> findByStockTransferChallanVO(StockTransferChallanVO stockTransferChallanVO);

}
