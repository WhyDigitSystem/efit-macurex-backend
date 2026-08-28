package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockTransferGrnDetailsVO;
import com.efitops.basesetup.entity.StockTransferGrnVO;

@Repository
public interface StockTransferGrnDetailsRepo extends JpaRepository<StockTransferGrnDetailsVO, Long> {

	List<StockTransferGrnDetailsVO> findByStockTransferGrnVO(StockTransferGrnVO vo);

}
