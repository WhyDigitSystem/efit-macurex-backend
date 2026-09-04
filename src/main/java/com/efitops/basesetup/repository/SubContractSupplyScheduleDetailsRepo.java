package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.SubContractSupplyScheduleDetailsVO;

@Repository
public interface SubContractSupplyScheduleDetailsRepo extends JpaRepository<SubContractSupplyScheduleDetailsVO, Long>{

}
