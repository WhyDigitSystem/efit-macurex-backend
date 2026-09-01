package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.MachineMasterVO;

public interface MachineMasterRepo extends JpaRepository<MachineMasterVO, Long> {

	List<MachineMasterVO> findByOrgIdAndBranch(Long orgId, BranchVO branchVO);
	
	
	@Query(nativeQuery = true, value =
	        "select concat(prefix, lpad(last_no, 5, 0)) AS docid " +
	        "from documenttypemapping_details " +
	        "where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getMachineMasterDocId(
	        Long orgId,
	        String financialYear,
	        String screenCode);

}
