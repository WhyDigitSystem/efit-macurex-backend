package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StockValuationVO;
@Repository
public interface StockValuationRepo extends JpaRepository<StockValuationVO, Long> {

}
