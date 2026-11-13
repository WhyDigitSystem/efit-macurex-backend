package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.GSTStateMasterVO;

public interface GSTStateMasterRepo extends JpaRepository<GSTStateMasterVO, Long> {
	
	@Query(value = """
	        SELECT *
	        FROM gststatemaster
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = false
	          AND active = true
	        ORDER BY state_name
	        """, nativeQuery = true)
	List<GSTStateMasterVO> findByGSTStateMasterByOrgId(@Param("orgId") Long orgId,
	                                            @Param("branch") Long branch);
	boolean existsByOrgIdAndStateCodeIgnoreCase(
	        Long orgId,
	        String stateCode);

}
