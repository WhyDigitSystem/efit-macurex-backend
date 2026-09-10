package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.EngineeringDeviationRequestVO;

public interface EngineeringDeviationRepo extends JpaRepository<EngineeringDeviationRequestVO, Long>{
	
	

	    @Query(value = """
	        SELECT *
	        FROM engineering_deviation_request_basic
	        WHERE org_id = ?1
	         
	          AND cancel = FALSE
	          AND active = TRUE
	        ORDER BY engineering_deviation_request_basic_id DESC
	        """, nativeQuery = true)
	    List<EngineeringDeviationRequestVO> getEngineeringDeviationByOrgId(
	            Long orgId);
	}


