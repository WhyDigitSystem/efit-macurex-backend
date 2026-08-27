package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockTransferGrnDetailsVO;

@Repository
public interface StockTransferGrnDetailsRepo extends JpaRepository<StockTransferGrnDetailsVO, Long> {

}
