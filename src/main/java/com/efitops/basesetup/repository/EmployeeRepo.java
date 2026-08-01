package com.efitops.basesetup.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.EmployeeVO;

public interface EmployeeRepo extends JpaRepository<EmployeeVO, Long> {

	@Query(value = "SELECT e.employeeCode , e.employeeName FROM EmployeeVO e WHERE e.orgId=?1")
	Set<Object[]> findAllNameAndEmployeeCodeByOrgId(Long orgId);

	List<EmployeeVO> findAllEmployeeByOrgId(Long orgId);

	boolean existsByEmployeeCodeAndOrgId(String employeeCode, Long orgId);

	@Query(value = """
				        SELECT
			    e.employeemaster_id,
			    e.employee_id,
			    e.emp_name
			FROM employeemaster e
			INNER JOIN designation d
			    ON d.designation_id = e.designation
			WHERE d.designation = 'PURCHASE'
			  AND e.org_id = ?1
			  AND e.branch = ?2
			  AND e.active = 1
			  AND e.cancel = 0
			ORDER BY e.emp_name
				        """, nativeQuery = true)
	List<Object[]> getPurchaseEmployees(Long orgId, Long branch);

}
