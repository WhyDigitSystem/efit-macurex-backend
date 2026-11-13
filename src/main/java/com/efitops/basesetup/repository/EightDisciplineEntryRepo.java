package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EightDisciplineEntryVO;

@Repository
public interface EightDisciplineEntryRepo extends JpaRepository<EightDisciplineEntryVO, Long> {
	
	
	 List<EightDisciplineEntryVO> findByOrgId(Long orgId);
	 
	 
	 @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid "
		        + "from documenttypemapping_details "
		        + "where org_id=?1 and fin_year=?2 and screen_code=?3")
		String getEightDisciplineEntryDocId(
		        Long orgId,
		        String financialYear,
		        String screenCode);
	 
	 
	 
	 
	  @Query(nativeQuery = true, value = "SELECT "
		        + "CM.customercomplaintmaster_id, "
		        + "CM.complaint_no, "
		        + "CM.customer, "
		        + "C.customer_code, "
		        + "C.customer_name, "
		        + "CM.item, "
		        + "I.item_code, "
		        + "I.item_description "
		        + "FROM customercomplaintmaster CM "
		        + "INNER JOIN customer_header C "
		        + "ON C.customer_id = CM.customer "
		        + "INNER JOIN item I "
		        + "ON I.item_id = CM.item "
		        + "WHERE CM.org_id = ?1 "
		        + "ORDER BY CM.complaint_no DESC")
		List<Object[]> getComplaintNoDropDownForEightDiscipline(
		        Long orgId);
		
		
		@Query(nativeQuery = true, value = "SELECT "
		        + "RCA.root_cause_analysis_basic_id AS id, "
		        + "RCA.doc_id AS name "
		        + "FROM root_cause_analysis_basic RCA "
		        + "WHERE RCA.org_id = ?1 "
		        + "AND RCA.complaint_no = ?2 "
		        + "ORDER BY RCA.root_cause_analysis_basic_id DESC")
		List<Object[]> getRootCauseNoDropDownForEightDiscipline(
		        Long orgId,
		        Long complaintNo);

	
}