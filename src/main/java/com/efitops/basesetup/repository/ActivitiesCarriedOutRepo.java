package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.ActivitiesCarriedOutVO;

public interface ActivitiesCarriedOutRepo extends JpaRepository<ActivitiesCarriedOutVO, Long>{
	
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
	String getActivitiesCarriedOutDocId(
	        @Param("orgId") Long orgId,
	        @Param("financialYear") String financialYear,
	        @Param("screenCode") String screenCode);
	
	@Query(value = """
	        SELECT *
	        FROM activities_carried_out_basic
	        WHERE cancel = false
	        AND active = true
	        AND org_id = :orgId
	        AND branch = :branch
	        ORDER BY activities_carried_out_basic_id DESC
	        """, nativeQuery = true)
	List<ActivitiesCarriedOutVO> findByOrgId(
	        @Param("orgId") Long orgId,
	        @Param ("branch") Long branch);
	
	@Query(value = """
	        SELECT 
	            P.pm_check_list_master_basic_id,
	            P.pm_check_list_no
	        FROM pm_check_list_master_basic P
	        INNER JOIN machine_equipments_master M 
	            ON M.pm_checklist_no = P.pm_check_list_no
	        WHERE M.machine_instrument_no = :MACNO
	          AND P.org_id = :orgId
	          AND P.branch = :branch
	          AND P.active = TRUE
	          AND P.cancel = FALSE

	        UNION

	        SELECT 
	            P.pm_check_list_master_basic_id,
	            P.pm_check_list_no
	        FROM pm_check_list_master_basic P
	        INNER JOIN tool_master_basic M 
	            ON M.pm_checklist_no = P.pm_check_list_no
	        WHERE M.tool_no = :MACNO
	          AND P.org_id = :orgId
	          AND P.branch = :branch
	          AND P.active = TRUE
	          AND P.cancel = FALSE

	        ORDER BY pm_check_list_no
	        """, nativeQuery = true)
	List<Object[]> getPMCheckListDropdownForActivitiesCarriedOut(
	        @Param("MACNO") String MACNO,
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);

//	List<Object[]> getPMCheckListDropdownForActivitiesCarriedOut(String mACNO, Long orgId, Long branch);

}
