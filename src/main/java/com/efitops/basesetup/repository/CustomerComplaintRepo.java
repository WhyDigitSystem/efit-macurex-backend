package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.efitops.basesetup.entity.CustomerComplaintEntryVO;
import com.efitops.basesetup.entity.EnquiryVO;
import com.efitops.basesetup.entity.RootCauseAnalysisVO;
import com.efitops.basesetup.entity.TransportMasterVO;

public interface CustomerComplaintRepo extends JpaRepository<CustomerComplaintEntryVO, Long> {

	CustomerComplaintEntryVO getCustomerComplaintById(Long id);

	@Query(value = """
			SELECT *
			FROM customercomplaintmaster
			WHERE org_id = :orgId
			  AND branch = :branch
			  AND cancel = false and active = 1
			ORDER BY customercomplaintmaster_id
			""", nativeQuery = true)
	List<CustomerComplaintEntryVO> getCustomerComplaintByOrgId(@Param("orgId") Long orgId,
			@Param("branch") Long branch);

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getCustomerComplaintDocId(Long orgId, String financialYear, String screenCode);

	
	
	






//	Optional<CustomerComplaintEntryVO> findBycustomercomplaintmasteid(
//	        String complaintNo);

	@Query(value = """
		    SELECT
		        CM.customercomplaintmaster_id,
		        CM.complaint_no,
		        CM.complaint_date,
		        CM.complaint_type,
		        P.customer_id,
		        P.customer_name,
		        I.item_id,
		        I.item_description,
		        CM.details_of_complaint
		    FROM customercomplaintmaster CM
		    JOIN customer_header P
		        ON P.customer_id = CM.customer
		    JOIN item I
		        ON I.item_id = CM.item
		    WHERE CM.org_id = :orgId
		      AND CM.branch = :branch
		      AND NOT EXISTS (
		          SELECT 1
		          FROM root_cause_analysis_basic RCA
		          WHERE RCA.complaint_no = CM.complaint_no
		      )
		    ORDER BY CM.complaint_no
		    """, nativeQuery = true)
		List<Object[]> getCustomerComplaintDropDownForRootCauseAnalysis(
		        @Param("orgId") Long orgId,
		        @Param("branch") Long branch);
		
		@Query(value = """
			    SELECT
			        I.item_id AS itemMasterId,
			        I.item_code AS itemId,
			        I.item_description AS itemDesc,
			        I.customer_part_no AS cpartno
			    FROM customercomplaintmaster CM
			    JOIN item I
			        ON I.item_id = CM.item
			    WHERE CM.complaint_no = :compino
			      AND CM.branch = :branch
			      AND CM.org_id = :orgId
			    """, nativeQuery = true)
			List<Object[]> getItemDropdownForRootCauseAnalysis(
			        @Param("compino") String compino,
			        @Param("branch") Long branch,
			        @Param("orgId") Long orgId);
		
		
		
//	@Query(value =
//		       "SELECT 'APPLIANCES' AS type " +
//		       "UNION " +
//		       "SELECT 'BOSCH' AS type " +
//		       "ORDER BY type",
//		       nativeQuery = true)
//		List<Object[]> getTypeDropdown();

}
