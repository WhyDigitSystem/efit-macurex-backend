package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.ProcessMasterVO;
@Repository
public interface ProcessMasterRepo extends JpaRepository<ProcessMasterVO, Long> {

	@Query(nativeQuery = true,value = "select * from processmaster where orgid=?1 and  branchcode=?2")
	List<ProcessMasterVO> getAllProcessMasterByOrgId(Long orgId, String branchCode);
	
	@Query(nativeQuery = true,value = "select * from processmaster where processmasterid=?1")
	List<ProcessMasterVO> getProcessMasterById(Long id);

	boolean existsByProcessNameAndOrgId(String processName, Long orgId);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and screencode=?4")
	String getProcessMasterDocId(Long orgId, String finYear, String branchCode, String screenCode);
}
