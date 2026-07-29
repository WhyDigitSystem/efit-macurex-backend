package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EmployeeComplianceDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;

@Repository
public interface EmployeeComplianceDetailsRepo extends JpaRepository<EmployeeComplianceDetailsVO, Long>{



//	@Modifying
//	@Query("delete from EmployeeComplianceDetailsVO c where c.employeeMasterVO = :vo")
//	int deleteByEmployeeMasterVO(@Param("vo") EmployeeMasterVO vo);

//	EmployeeComplianceDetailsVO findByEmployeeMasterVO(EmployeeMasterVO saved);
	

}
