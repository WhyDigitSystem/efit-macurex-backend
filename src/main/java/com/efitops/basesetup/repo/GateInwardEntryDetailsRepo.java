package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GateInwardEntryDetailsVO;
import com.efitops.basesetup.entity.GateInwardEntryVO;

@Repository
public interface GateInwardEntryDetailsRepo extends JpaRepository<GateInwardEntryDetailsVO, Long>{

	List<GateInwardEntryDetailsVO> findByGateInwardEntryVO(GateInwardEntryVO gateInwardEntryVO);

}
