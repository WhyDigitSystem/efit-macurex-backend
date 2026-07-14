package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.GateOutwardEntryDetailsVO;
import com.efitops.basesetup.entity.GateOutwardEntryVO;

@Repository
public interface GateOutwardEntryDetailsRepo extends JpaRepository<GateOutwardEntryDetailsVO, Long>{

	List<GateOutwardEntryDetailsVO> findByGateOutwardEntryVO(GateOutwardEntryVO gateOutwardEntryVO);


}
