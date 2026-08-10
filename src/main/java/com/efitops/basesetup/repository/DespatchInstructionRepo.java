package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.DespatchInstructionVO;

public interface DespatchInstructionRepo extends JpaRepository<DespatchInstructionVO, Long> {

	
 
	@Query(value = """
	        SELECT *
	        FROM despatch_basic
	        WHERE org_id = :orgId
	          AND branch = :branch
	          AND cancel = false
	          AND active = 1
	        ORDER BY di_no
	        """, nativeQuery = true)
	List<DespatchInstructionVO> getDespatchInstructionByOrgId(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
}
