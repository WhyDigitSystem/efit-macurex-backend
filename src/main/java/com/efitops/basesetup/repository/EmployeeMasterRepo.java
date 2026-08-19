package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EmployeeMasterVO;

@Repository
public interface EmployeeMasterRepo extends JpaRepository<EmployeeMasterVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefix,lpad(last_no,4,0)) AS docid from documenttypemapping_details where org_id=?1 and screen_code=?2")
	String getEmployeeByDocId(Long orgId, String screenCode);

	@Query(nativeQuery = true, value = "select * from employeemaster where employeemaster_id=?1 and active=1 and cancel=0")
	EmployeeMasterVO getEmployeeMasterById(Long id);

	@Query(nativeQuery = true, value = "select * from employeemaster where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<EmployeeMasterVO> getEmployeeMasterByOrgId(Long orgId, Long branchId);
	
	@Query(nativeQuery = true,
		       value = "SELECT employeemaster_id, employee_id, emp_name " +
		               "FROM employeemaster " +
		               "WHERE org_id=?1 and branch=?2 and department = ?3 " +
		               "AND active = 1 " +
		               "AND cancel = 0 " +
		               "ORDER BY emp_name")
		List<Object[]> getPreparedBy(Long departmentId, Long branch, Long departmentId2);
  
	@Query(nativeQuery = true, value = "select * from employeemaster where org_id=?1 and active=1 and cancel=0")
	List<EmployeeMasterVO> getEmployeeMasterByOrgId(Long orgId);
	
	
	@Query(value = """
	        SELECT employeemaster_id,
	               employee_id,
	               emp_name
	        FROM employeemaster
	        WHERE active = 1
	          AND cancel = 0
	          AND org_id = :orgId
	          AND branch = :branch
	        ORDER BY emp_name
	        """, nativeQuery = true)
	List<Object[]> getPurchaseIndentPreparedByDropdown(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
	
	
	@Query(value = """
	        SELECT employeemaster_id,
	               employee_id,
	               emp_name
	        FROM employeemaster
	        WHERE active = 1
	          AND cancel = 0
	          AND org_id = :orgId
	          AND branch = :branch
	        ORDER BY emp_name
	        """, nativeQuery = true)
	List<Object[]> getPurchaseIndentByWhomDropdown(
	        @Param("orgId") Long orgId,
	        @Param("branch") Long branch);
}
