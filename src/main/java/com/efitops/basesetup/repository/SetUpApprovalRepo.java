package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.SetUpApprovalVO;

public interface SetUpApprovalRepo extends JpaRepository<SetUpApprovalVO, Long>{

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,5,0)) AS docid from documenttypemapping_details where org_id=?1 and fin_year=?2 and  screen_code=?3")
	String getSetUpApprovalDocId(Long orgId, String financialYear, String screenCode);

}
