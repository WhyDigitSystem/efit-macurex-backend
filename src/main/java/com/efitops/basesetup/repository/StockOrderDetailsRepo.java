package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockOrderDetailsVO;
import com.efitops.basesetup.entity.StockOrderVO;

@Repository
public interface StockOrderDetailsRepo extends JpaRepository<StockOrderDetailsVO, Long>{

	List<StockOrderDetailsVO> findByStockOrderVO(StockOrderVO stockOrderVO);

}
