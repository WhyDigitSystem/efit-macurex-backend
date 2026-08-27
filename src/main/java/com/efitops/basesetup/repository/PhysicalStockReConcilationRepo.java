package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.PhysicalStockReConcilationVO;

public interface PhysicalStockReConcilationRepo extends JpaRepository<PhysicalStockReConcilationVO, Long> {

	@Query(value = """
			SELECT *
			FROM physical_stock_reconcilation_basic
			WHERE org_id = ?1
			  AND branch = ?2
			  AND cancel = FALSE
			ORDER BY physical_stock_reconcilation_basic_id DESC
			""", nativeQuery = true)
	List<PhysicalStockReConcilationVO> getPhysicalStockReConcilationByOrgId(Long orgId, Long branch);

//	String getPhysicalStockReConcilationDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
			SELECT
			    l.id,
			    l.location_id,
			    l.location_name
			FROM location l
			INNER JOIN listofvaluesdetails lov
			    ON l.location_type = lov.listofvaluesdetails_id
			INNER JOIN branch b
			    ON l.branch = b.branch_id
			WHERE l.location_type = ?1
			  AND l.branch = ?2
			  AND l.org_id = ?3
			  AND l.active = TRUE
			  AND l.cancel = FALSE
			  AND lov.active = TRUE
			  AND b.active = TRUE
			  AND b.cancel = FALSE
			ORDER BY l.location_name
			""", nativeQuery = true)
	List<Object[]> getLocationDropdownForPhysicalStockReConcilation(Long locationType, Long branch, Long orgId);

	@Query(nativeQuery = true, value = """
			SELECT concat(prefix, lpad(last_no, 5, 0)) AS docid
			FROM documenttypemapping_details
			WHERE org_id = ?1
			  AND fin_year = ?2
			  AND screen_code = ?3
			""")
	String getPhysicalStockReConcilationDocId(Long orgId, String financialYear, String screenCode);
}
