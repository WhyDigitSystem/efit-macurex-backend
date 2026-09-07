package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractSupplyScheduleItemDetailsVO;

@Repository
public interface SubContractSupplyScheduleItemDetailsRepo extends JpaRepository<SubContractSupplyScheduleItemDetailsVO, Long> {

}
