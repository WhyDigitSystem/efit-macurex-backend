package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.SetUpApprovalVO;

public interface SetUpApprovalRepo extends JpaRepository<SetUpApprovalVO, Long> {

	@Query(value = """
			SELECT concat(prefix, lpad(last_no, 5, 0)) AS docid
			FROM documenttypemapping_details
			WHERE org_id = ?1
			  AND fin_year = ?2
			  AND screen_code = ?3
			""", nativeQuery = true)
	String getSetUpApprovalDocId(Long orgId, String financialYear, String screenCode);

	@Query(value = """
			SELECT *
			FROM set_up_approval_basic
			WHERE active = 1
			AND cancel = 0
			AND org_id = :orgId
			  AND branch = :branch
			""", nativeQuery = true)
	List<SetUpApprovalVO> getSetUpApprovalByOrgId(@Param("orgId") Long orgId, @Param("branch") Long branch);

	@Query(value = """
			SELECT
			    i.item_id AS itemId,
			    i.item_code AS itemCode,
			    i.item_description AS itemDescription,
			    i.customer_part_no AS customerPartNo,
			    lv.value_description AS itemType,
			     i.drawing_no AS drawingNo
			FROM item i
			INNER JOIN listofvaluesdetails lv
			    ON lv.listofvaluesdetails_id = i.item_type
			WHERE i.org_id = :orgId
			  AND i.branch = :branch
			  AND i.active = 1
			  AND i.cancel = 0
			  AND lv.active = 1
			  AND lv.listofvalues_id = 1000000015
			  AND UPPER(lv.value_description) IN ('FG', 'SFG')
			""", nativeQuery = true)
	List<Object[]> getFgSfgItemDropdownForSetUpApproval(@Param("orgId") Long orgId, @Param("branch") Long branch);
	
	@Query(value = """
	        SELECT
	            pscrb.process_sheet_comp_routing_basic_id AS id,
	            pscrb.doc_id AS processSheetNo
	        FROM process_sheet_comp_routing_basic pscrb
	        INNER JOIN item i
	            ON i.item_id = pscrb.fg_sfg_item_code
	        WHERE pscrb.fg_sfg_item_code = :item
	          AND pscrb.org_id = :orgId
	          AND pscrb.branch = :branch
	          AND pscrb.active = 1
	          AND pscrb.cancel = 0
	          AND i.active = 1
	          AND i.cancel = 0
	        ORDER BY pscrb.process_sheet_comp_routing_basic_id DESC
	        """, nativeQuery = true)
	List<Object[]> getProcessSheetNoForSetUpApproval(
	        @Param("item") Long item,
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
	@Query(value = """
	        SELECT
	            cpb.control_plan_basic_id AS controlPlanId,
	            cpb.plan_no AS controlPlanNo,
	            cpd.control_plan_detail_id AS controlPlanDetailId,
	            cpd.operation_no AS operationNo,
	            cpd.process AS description,
	            cpd.specification AS specification
	        FROM control_plan_basic cpb
	        INNER JOIN control_plan_detail cpd
	            ON cpd.control_plan_basic_id = cpb.control_plan_basic_id
	        INNER JOIN item i
	            ON i.item_id = cpb.fg_item_code
	        WHERE cpb.fg_item_code = :item
	          AND cpb.process_sheet_no = :processSheetNo
	          AND cpb.org_id = :orgId
	          AND cpb.branch = :branch
	          AND cpb.active = 1
	          AND cpb.cancel = 0
	          AND cpb.approved = 1
	          AND i.active = 1
	          AND i.cancel = 0
	        ORDER BY
	            cpb.control_plan_basic_id DESC,
	            cpd.control_plan_detail_id
	        """, nativeQuery = true)
	List<Object[]> getControlPlanDetailsForSetUpApproval(
	        @Param("item") Long item,
	        @Param("processSheetNo") String processSheetNo,
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch)
	;
}
