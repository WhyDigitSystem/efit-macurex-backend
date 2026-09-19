package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.InitialSampleInspectionVO;

public interface InitialSampleInspectionRepo  extends JpaRepository<InitialSampleInspectionVO, Long> {
	
	
	  List<InitialSampleInspectionVO> findByOrgIdAndBranch_Id(
	            Long orgId,
	            Long branch);

	    @Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid "
	            + "from documenttypemapping_details "
	            + "where org_id=?1 and fin_year=?2 and screen_code=?3")
	    String getInitialSampleInspectionDocId(
	            Long orgId,
	            String financialYear,
	            String screenCode);
	    
	    
	    @Query(nativeQuery = true, value = "SELECT "
	            + "CH.customer_id AS id, "
	            + "CH.customer_code AS name "
	            + "FROM customer_header CH "
	            + "WHERE CH.org_id = ?1 "
	            + "AND CH.branch = ?2 "
	            + "AND CH.customer_category = 1000000154 "
	            + "AND CH.active = TRUE "
	            + "AND CH.cancel = FALSE "
	            + "ORDER BY CH.customer_name")
	    List<Object[]> getSupplierIdDropDownForInitialSampleInspection(
	            Long orgId,
	            Long branch);

}
