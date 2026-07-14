package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.RackStockDetailsVO;

public interface RackStockDetailsRepo extends JpaRepository<RackStockDetailsVO, Long> {

}
