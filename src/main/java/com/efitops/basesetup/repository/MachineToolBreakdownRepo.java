package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.MachineToolBreakdownVO;

public interface MachineToolBreakdownRepo extends JpaRepository<MachineToolBreakdownVO, Long> {

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
	String getMachineToolBreakdownDocId(@Param("orgId") Long orgId, @Param("financialYear") String financialYear,
			@Param("screenCode") String screenCode);

	@Query(value = """
			SELECT *
			FROM machine_tool_breakdown_basic
			WHERE cancel = false
			  AND active = true
			  AND org_id = :orgId
			  AND branch = :branch
			ORDER BY machine_tool_breakdown_basic_id DESC
			""", nativeQuery = true)
	List<MachineToolBreakdownVO> findByOrgIdAndBranchId(@Param("orgId") Long orgId, @Param("branch") Long branch);

	@Query(value = """
			SELECT
			    me.machine_instrument_name AS name,
			    me.machine_instrument_no AS number,
			    l.location_name AS location
			FROM machine_equipments_master me
			INNER JOIN tool_category_detail td
			    ON td.tool_category_detail_id = me.machine_instrument_category
			INNER JOIN location l
			    ON me.location = l.id
			WHERE td.tool_category_basic_id = :toolCategoryId
			  AND me.org_id = :orgId
			  AND me.branch = :branch
			  AND me.active = TRUE
			  AND me.cancel = FALSE

			UNION

			SELECT
			    t.tool_name AS name,
			    t.tool_no AS number,
			    l.location_name AS location
			FROM tool_master_basic t
			INNER JOIN tool_category_basic tc
			    ON tc.apllicable_for = t.tool_category
			INNER JOIN location l
			    ON t.location = l.id
			WHERE tc.tool_category_basic_id = :toolCategoryId
			  AND t.org_id = :orgId
			  AND t.branch = :branch
			  AND t.active = TRUE
			  AND t.cancel = FALSE
			""", nativeQuery = true)
	List<Object[]> getMachineToolForBreakdown(@Param("toolCategoryId") Long toolCategoryId, @Param("orgId") Long orgId,
			@Param("branch") Long branch);

}
