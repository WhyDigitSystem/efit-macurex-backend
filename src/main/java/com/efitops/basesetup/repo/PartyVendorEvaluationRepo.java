package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PartyMasterVO;
import com.efitops.basesetup.entity.PartyVendorEvaluationVO;

@Repository
public interface PartyVendorEvaluationRepo extends JpaRepository<PartyVendorEvaluationVO, Long> {

	PartyVendorEvaluationVO findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
