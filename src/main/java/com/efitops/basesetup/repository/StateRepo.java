package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.StateVO;

@Repository
public interface StateRepo extends JpaRepository<StateVO, Long> {


	@Query(value = "SELECT * FROM state WHERE org_id = ?1 AND country = ?2", nativeQuery = true)
	List<StateVO> findByCountry(Long orgid,Long country);

	@Query("select a from StateVO a where a.orgId=?1")
	List<StateVO> findAllByOrgId(Long orgid);

	boolean existsByStateCodeAndOrgId(String stateCode, Long orgId);

	boolean existsByStateNumberAndOrgId(String stateNumber, Long orgId);

	boolean existsByStateNameAndOrgId(String stateName, Long orgId);

	//boolean existsByStateCodeAndStateNameAndStateNumberAndOrgId(String stateCode, String stateName, String stateNumber,Long orgId);

	
}


