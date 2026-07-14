package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.PartyMasterVO;
import com.efitops.basesetup.entity.PartySalesPersonTaggingVO;

@Repository
public interface PartySalesPersonTaggingRepo extends JpaRepository<PartySalesPersonTaggingVO, Long> {

	List<PartySalesPersonTaggingVO> findByPartyMasterVO(PartyMasterVO partyMasterVO);

}
