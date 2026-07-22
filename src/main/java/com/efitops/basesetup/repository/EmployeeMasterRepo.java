package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EmployeeMasterVO;

@Repository
public interface EmployeeMasterRepo extends JpaRepository<EmployeeMasterVO, Long>{

	@Query(nativeQuery = true,value="select * from  employeemaster where orgid=?1 and branchcode=?2")
	List<EmployeeMasterVO> getAllEmployeeMasterByOrgId(Long orgId, String branchCode);

	@Query(nativeQuery = true,value="select * from  employeemaster where employeemasterid=?1")
	List<EmployeeMasterVO> getEmployeeMasterById(Long id);


	EmployeeMasterVO findByEmployeeCode(String employeeCode);

	boolean existsByEmployeeCodeAndOrgId(String employeeCode, Long orgId);

	@Query("SELECT e FROM EmployeeMasterVO e " +
		       "JOIN e.employeeDetailsVO d " +
		       "WHERE d.designation IN :designations")
		List<EmployeeMasterVO> findByDesignationIn(@Param("designations") List<String> designations);

//	@Query("""
//			SELECT e FROM EmployeeMasterVO e
//			LEFT JOIN FETCH e.employeeDetailsVO
//			LEFT JOIN FETCH e.employeePersonalDetailsVO
//			LEFT JOIN FETCH e.employeeCommunicationDetailsVO
//			LEFT JOIN FETCH e.employeeComplianceDetailsVO
//			LEFT JOIN FETCH e.employeeFinanceInformationVO
//			LEFT JOIN FETCH e.employeeLoanDetailsVO
//			LEFT JOIN FETCH e.documents
//			WHERE e.id = :id
//			""")
//			Optional<EmployeeMasterVO> findByIdWithAllDetails(@Param("id") Long id);
}
