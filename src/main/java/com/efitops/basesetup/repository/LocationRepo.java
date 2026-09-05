package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.LocationVO;

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
	List<LocationVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branch") Long branch);

	@Query(value = """
			SELECT
			    lm.id AS id,
			    lm.location_id AS locationId,
			    lm.location_name AS locationName

			FROM locationmaster lm

			JOIN listofvaluesdetails lov
			    ON lov.id = lm.location_type

			WHERE lm.org_id = :orgId
			  AND lm.branch = :branch
			  AND lov.description = 'SUB CONTRACT'
			  AND lm.active = 1
			  AND lm.cancel = 0

			ORDER BY lm.location_name
			""", nativeQuery = true)
	Set<Object[]> getLocationForDeliverChallanSubContract(@Param("orgId") Long orgId, @Param("branch") Long branch);


}
