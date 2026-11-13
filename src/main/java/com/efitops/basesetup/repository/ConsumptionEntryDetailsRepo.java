package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ConsumptionEntryDetailsVO;
import com.efitops.basesetup.entity.ConsumptionEntryVO;

@Repository
public interface ConsumptionEntryDetailsRepo extends JpaRepository<ConsumptionEntryDetailsVO, Long> {

	List<ConsumptionEntryDetailsVO> findByConsumptionEntryVO(ConsumptionEntryVO vo);
}
