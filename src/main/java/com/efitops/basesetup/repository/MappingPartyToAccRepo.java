package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.MappingOfPartyToAccVO;

public interface MappingPartyToAccRepo extends JpaRepository<MappingOfPartyToAccVO, Long>{

	MappingOfPartyToAccVO getMappingOfPartyToAccById(Long id);

	@Query(value = "SELECT * FROM partyacc WHERE org_id = ?1 AND branch = ?2", nativeQuery = true)
	List<MappingOfPartyToAccVO> getMappingOfPartyToAccByOrgId(Long orgId, Long branch);

}
