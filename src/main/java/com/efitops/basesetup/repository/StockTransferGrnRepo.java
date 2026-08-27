package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockTransferGrnVO;

@Repository
public interface StockTransferGrnRepo extends JpaRepository<StockTransferGrnVO, Long> {

}
