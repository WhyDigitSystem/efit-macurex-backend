package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RmConsumptionEntryDetailsVO;
import com.efitops.basesetup.entity.ConsumptionEntryVO;

@Repository
public interface RmConsumptionEntryDetailsRepo extends JpaRepository<RmConsumptionEntryDetailsVO, Long> {

	List<RmConsumptionEntryDetailsVO> findByConsumptionEntryVO(ConsumptionEntryVO vo);
}
