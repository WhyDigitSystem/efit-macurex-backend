package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.MachineMasterVO;

public interface MachineMasterRepo extends JpaRepository<MachineMasterVO, Long> {

	List<MachineMasterVO> findByOrgIdAndBranch(Long orgId, BranchVO branchVO);
	
	@Query(value = "SELECT * FROM machine_equipments_master " +
            "WHERE machine_equipments_master_id = ?1",
    nativeQuery = true)
Optional<MachineMasterVO> findMachineById(Long id);
	
	@Query(nativeQuery = true, value =
	        "select concat(prefix, lpad(last_no, 5, 0)) AS docid " +
	        "from documenttypemapping_details " +
	        "where org_id=?1 and fin_year=?2 and screen_code=?3")
	String getMachineMasterDocId(
	        Long orgId,
	        String financialYear,
	        String screenCode);

}
