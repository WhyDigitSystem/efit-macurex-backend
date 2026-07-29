package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DailyExchangeRateVO;

@Repository
public interface DailyExchangeRateRepo extends JpaRepository<DailyExchangeRateVO, Long>{

}
