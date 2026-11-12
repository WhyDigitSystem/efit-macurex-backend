package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.UnitMasterVO;
import com.efitops.basesetup.entity.UomConversionVO;

public interface UomConversionRepo extends JpaRepository<UomConversionVO, Long> {

	@Query(value = """
			SELECT *
			FROM uomconversion
			WHERE org_id = :orgId
			  AND branch = :branch
			  AND cancel = false
			  AND active = true
			ORDER BY uomconversion_id
			""", nativeQuery = true)
	List<UomConversionVO> findByOrgIdAndBranch(@Param("orgId") Long orgId, @Param("branch") Long branch);

//	List<UomConversionVO> getUomConversionByOrgId(Long orgId, Long branchId);
	boolean existsByOrgIdAndFromUnitAndToUnit(Long orgId, UnitMasterVO fromUnit, UnitMasterVO toUnit);
	
	
	@Query(value = """
	        SELECT
	            uomconversion_id,
	            multiplication_factor
	        FROM uomconversion
	        WHERE active = 1
	          AND cancel = 0
	          AND org_id = :orgId
	          AND branch = :branch
	          AND from_unit = :fromUnit
	          AND to_unit = :toUnit
	        ORDER BY multiplication_factor
	        """, nativeQuery = true)
	List<Object[]> getPurchaseIndentConversionFactorDropdown(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch,
	        @Param("fromUnit") Long fromUnit,
	        @Param("toUnit") Long toUnit);
}