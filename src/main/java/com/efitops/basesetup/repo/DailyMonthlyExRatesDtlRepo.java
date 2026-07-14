package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.DailyMonthlyExRatesDtlVO;

public interface DailyMonthlyExRatesDtlRepo extends JpaRepository<DailyMonthlyExRatesDtlVO, Long>{

}
