package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractSupplyScheduleVO;

@Repository
public interface SubContractSupplyScheduleRepo extends JpaRepository<SubContractSupplyScheduleVO, Long> {

}
