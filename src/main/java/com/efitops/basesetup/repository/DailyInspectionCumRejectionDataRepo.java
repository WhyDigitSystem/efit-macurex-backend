package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.DailyInspectionCumRejectionDataVO;

public interface DailyInspectionCumRejectionDataRepo extends JpaRepository<DailyInspectionCumRejectionDataVO, Long> {

	@Query(value = """
			SELECT concat(prefix, lpad(last_no, 5, 0)) AS docid
			FROM documenttypemapping_details
			WHERE org_id = ?1
			  AND fin_year = ?2
			  AND screen_code = ?3
			""", nativeQuery = true)
	String getDailyInspectionCumRejectionDataDocId(Long orgId, String financialYear, String screenCode);
	
	@Query(value = """
	        SELECT *
	        FROM daily_inspection_cum_rejection_date_basic
	        WHERE cancel = 0
	        AND active = 1
	        AND org_id = :orgId
	          AND branch = :branch
	        """, nativeQuery = true)
	List<DailyInspectionCumRejectionDataVO>
	getDailyInspectionCumRejectionDataByOrgId(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);

	
	@Query(value = """
	        SELECT
	            l.id AS locationId,
	            l.location_name AS locationName
	        FROM location l
	        WHERE UPPER(l.location_id) LIKE 'Q%'
	          AND l.org_id = :orgId
	          AND l.branch = :branch
	          AND l.active = 1
	          AND l.cancel = 0
	        """, nativeQuery = true)
	List<Object[]> getFromLocationDropdownForDailyInspectionCumRejection(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
//	rework location
	@Query(value = """
	        SELECT
	            l.id AS id,
	            l.location_id AS locationId,
	            l.location_name AS locationName
	        FROM location l
	        WHERE l.location_id LIKE 'REWORK%'
	          AND l.branch = :branch
	          AND l.org_id = :orgId
	          AND l.active = TRUE
	          AND l.cancel = FALSE
	        ORDER BY l.location_id
	        """, nativeQuery = true)
	List<Object[]> getReworkLocationDropdownForDailyInspectionCumRejection(
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId);
	
//	rejection location
	
	@Query(value = """
	        SELECT
	            l.id AS id,
	            l.location_id AS locationId,
	            l.location_name AS locationName
	        FROM location l
	        WHERE l.location_id LIKE 'REJECTION%'
	          AND l.branch = :branch
	          AND l.org_id = :orgId
	          AND l.active = TRUE
	          AND l.cancel = FALSE
	        ORDER BY l.location_id
	        """, nativeQuery = true)
	List<Object[]> getRejectionLocationDropdownForDailyInspectionCumRejection(
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId);
	
	
	@Query(value = """
	        SELECT
	            l.id AS id,
	            l.location_id AS locationId,
	            l.location_name AS locationName
	        FROM location l
	        WHERE l.location_id LIKE 'SCRAP%'
	          AND l.branch = :branch
	          AND l.org_id = :orgId
	          AND l.active = TRUE
	          AND l.cancel = FALSE
	        ORDER BY l.location_id
	        """, nativeQuery = true)
	List<Object[]> getScrapLocationDropdownForDailyInspectionCumRejection(
	        @Param("branch") Long branch,
	        @Param("orgId") Long orgId);
}


