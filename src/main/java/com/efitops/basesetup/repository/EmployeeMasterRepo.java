package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EmployeeMasterVO;

@Repository
public interface EmployeeMasterRepo extends JpaRepository<EmployeeMasterVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,4,0)) AS docid from documenttypemappingdetails where orgid=?1 and screencode=?2")
	String getEmployeeByDocId(Long orgId, String screenCode);

	
	@Query(nativeQuery = true, value = "select * from employeemaster where employeemaster_id=?1 and active=1 and cancel=0")
	EmployeeMasterVO getEmployeeMasterById(Long id);

}
