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
}