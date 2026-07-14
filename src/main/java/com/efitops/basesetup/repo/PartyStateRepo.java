package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PartyMasterVO;
import com.efitops.basesetup.entity.PartyStateVO;

@Repository
public interface PartyStateRepo extends JpaRepository<PartyStateVO, Long>{


	List<PartyStateVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
