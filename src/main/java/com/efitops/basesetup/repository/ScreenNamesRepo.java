package com.efitops.basesetup.repository;


import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.ScreenNamesVO;

public interface ScreenNamesRepo extends JpaRepository<ScreenNamesVO, Long> {

	
	@Query(nativeQuery = true, value = "select * from screenname where screennameid=?1")
	List<ScreenNamesVO> findFinScreenById(Long id);


	boolean existsByScreenName(String screenName);

	boolean existsByScreenCode(String screenCode);

	@Query(nativeQuery = true, value = "select screencode,screenname from screenname where screencode not in (select screen_code from document_type_master WHERE org_id=?1)")
	Set<Object[]> findAllScreenCode(Long orgId);



}


