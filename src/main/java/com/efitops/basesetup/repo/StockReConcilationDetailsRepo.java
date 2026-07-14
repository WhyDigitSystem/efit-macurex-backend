package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockReConcilationDetailsVO;
import com.efitops.basesetup.entity.StockReConcilationVO;

@Repository
public interface StockReConcilationDetailsRepo extends JpaRepository<StockReConcilationDetailsVO, Long> {

	List<StockReConcilationDetailsVO> findByStockReConcilationVO(StockReConcilationVO stockReConcilationVO);

}
