package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.LocationVO;
import com.efitops.basesetup.entity.TransportMasterVO;

public interface LocationRepo extends JpaRepository<LocationVO, Long> {

	boolean existsByLocationIdAndOrgId(String locationId, Long orgId);

	

	@Query(value = """
	        SELECT *
	        FROM location
	        WHERE org_id = :orgId
	          AND branch= :branch
	          AND cancel = false and active = 1
	        ORDER BY location_id
	        """, nativeQuery = true)
	List<LocationVO> findByOrgIdAndBranch(@Param("orgId") Long orgId,
	                                             @Param("branch") Long branch);



	

}
