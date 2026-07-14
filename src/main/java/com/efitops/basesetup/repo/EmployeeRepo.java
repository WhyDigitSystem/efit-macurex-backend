package com.efitops.basesetup.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.EmployeeVO;

public interface EmployeeRepo extends JpaRepository<EmployeeVO,Long>{

	@Query(value = "SELECT e.employeeCode , e.employeeName FROM EmployeeVO e WHERE e.orgId=?1")
	Set<Object[]> findAllNameAndEmployeeCodeByOrgId(Long orgId);

	List<EmployeeVO> findAllEmployeeByOrgId(Long orgId);

	boolean existsByEmployeeCodeAndOrgId(String employeeCode, Long orgId);

	EmployeeMasterVO getAllEmployeeMasterById(Long id);


	


}
  
