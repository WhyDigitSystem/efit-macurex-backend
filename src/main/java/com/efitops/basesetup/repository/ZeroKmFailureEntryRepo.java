package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efitops.basesetup.entity.ZeroKmFailureEntryVO;

public interface ZeroKmFailureEntryRepo extends JpaRepository<ZeroKmFailureEntryVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getZeroKmFailureEntryDocId(Long orgId, String financialYear, String screenCode);

	@Query(nativeQuery = true, value = "select * from zero_km_failure_entry_basic where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<ZeroKmFailureEntryVO> findByOrgIdAndBranch_Id(Long orgId, Long branch);
}