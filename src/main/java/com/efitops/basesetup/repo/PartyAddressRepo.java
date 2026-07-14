package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PartyAddressVO;
import com.efitops.basesetup.entity.PartyMasterVO;

@Repository
public interface PartyAddressRepo extends JpaRepository<PartyAddressVO, Long>{

	List<PartyAddressVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
