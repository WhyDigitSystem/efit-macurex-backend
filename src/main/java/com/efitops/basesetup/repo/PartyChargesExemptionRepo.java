package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PartyChargesExemptionVO;
import com.efitops.basesetup.entity.PartyMasterVO;

@Repository
public interface PartyChargesExemptionRepo extends JpaRepository<PartyChargesExemptionVO, Long>{

	List<PartyChargesExemptionVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
