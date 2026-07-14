package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PartyMasterVO;
import com.efitops.basesetup.entity.PartySpecialTDSVO;

@Repository
public interface PartySpecialTDSRepo extends JpaRepository<PartySpecialTDSVO, Long>{

	List<PartySpecialTDSVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
