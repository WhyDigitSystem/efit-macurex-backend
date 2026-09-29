package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.InitialStageInspectionVO;

public interface InitialStageInspectionRepo extends JpaRepository<InitialStageInspectionVO, Long> {
	
	
	   List<InitialStageInspectionVO> findByOrgIdAndBranch_Id(
	            Long orgId,
	            Long branch);
	
	
	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid "
	        + "from documenttypemapping_details "
	        + "where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getInitialStageInspectionDocId(
	        Long orgId,
	        String financialYear,
	        String screenCode);
	
	
	
	
	@Query(nativeQuery = true, value = "SELECT "
	        + "JOB.job_order_basic_id AS id, "
	        + "JOB.doc_id AS name "
	        + "FROM job_order_basic JOB "
	        + "WHERE JOB.org_id = ?1 "
	        + "AND JOB.branch = ?2 "
	        + "AND JOB.vendor = ?3 "
	        + "AND JOB.active = TRUE "
	        + "AND JOB.cancel = FALSE "
	        + "AND JOB.doc_id IS NOT NULL "
	        + "ORDER BY JOB.job_order_basic_id DESC")
	List<Object[]> getWorkOrderNoDropDownForInitialStageInspection(
	        Long orgId,
	        Long branch,
	        Long partyId);
	
	
	@Query(nativeQuery = true, value = """
	        SELECT
	            ps.process_sheet_comp_routing_basic_id AS id,
	            ps.doc_id AS name
	        FROM process_sheet_comp_routing_basic ps
	        WHERE ps.org_id = ?1
	          AND ps.branch = ?2
	          AND ps.fg_sfg_item_code = ?3
	          AND ps.active = TRUE
	          AND ps.cancel = FALSE
	          AND ps.doc_id IS NOT NULL
	        ORDER BY ps.process_sheet_comp_routing_basic_id DESC
	        """)
	List<Object[]> getProcessSheetNoDropdownForInitialStageInspection(
	        Long orgId,
	        Long branch,
	        Long itemId);
	
	
	@Query(nativeQuery = true, value =
		       "SELECT "
		     + "cpd.operation_no AS operationNo, "
		     + "'' AS mark, "
		     + "pmb.parameter_description AS parametersToBeChecked, "
		     + "cpd.specification AS specification "
		     + "FROM control_plan_basic cpb "
		     + "INNER JOIN control_plan_detail cpd "
		     + "ON cpd.control_plan_basic_id = cpb.control_plan_basic_id "
		     + "INNER JOIN control_plan_parameter cpp "
		     + "ON cpp.control_plan_basic_id = cpb.control_plan_basic_id "
		     + "INNER JOIN parameter_master_basic pmb "
		     + "ON pmb.parameter_master_basic_id = cpp.parameter "
		     + "WHERE cpb.fg_item_code = ?1 "
		     + "AND cpb.active = TRUE "
		     + "AND cpb.cancel = FALSE "
		     + "ORDER BY cpd.control_plan_detail_id")
		List<Object[]> getControlPlanDetailsByItemIdForInitialStageInspection(Long itemId);
		
		
		

}
