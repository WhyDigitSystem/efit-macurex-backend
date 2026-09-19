package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.CauseMasterVO;

public interface CauseMasterRepo extends JpaRepository<CauseMasterVO, Long>{
	
	 @Query(value = """
	            SELECT *
	            FROM cause_master_basic
	            WHERE cause_master_basic_id = :id
	            """, nativeQuery = true)
	    CauseMasterVO getCauseMasterById(@Param("id") Long id);
	 
	  @Query(value = """
	            SELECT *
	            FROM cause_master_basic
	            WHERE cancel = 0
	            AND active = 1
	            AND org_id = :orgId
	            ORDER BY cause_master_basic_id DESC
	            """, nativeQuery = true)
	    List<CauseMasterVO> getCauseMasterByOrgId(@Param("orgId") Long orgId);

}
