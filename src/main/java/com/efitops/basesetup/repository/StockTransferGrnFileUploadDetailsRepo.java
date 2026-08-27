package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockTransferGrnFileUploadDetailsVO;

@Repository
public interface StockTransferGrnFileUploadDetailsRepo extends JpaRepository<StockTransferGrnFileUploadDetailsVO, Long> {

}
