package com.efitops.basesetup.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.ScrapMaterialReturnRejectionVO;

public interface ScrapMaterialReturnRejectionRepo extends JpaRepository<ScrapMaterialReturnRejectionVO, Long> {

	@Query(value = """
			SELECT CONCAT(
			    dtmd.prefix,
			    LPAD(dtmd.last_no + 1, 5, '0')
			)
			FROM documenttypemapping_details dtmd
			WHERE dtmd.org_id = :orgId
			  AND dtmd.fin_year = :financialYear
			  AND dtmd.screen_code = :screenCode
			""", nativeQuery = true)
	String getScrapMaterialReturnRejectionDocId(@Param("orgId") Long orgId,
			@Param("financialYear") String financialYear, @Param("screenCode") String screenCode);

	@Query(value = """
			SELECT *
			FROM scrap_material_return_rejection_basic
			WHERE active = true
			  AND cancel = false
			  AND org_id = :orgId
			  AND branch = :branch
			ORDER BY scrap_material_return_rejection_basic_id DESC
			""", nativeQuery = true)
	List<ScrapMaterialReturnRejectionVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branch") Long branch);

	
}
