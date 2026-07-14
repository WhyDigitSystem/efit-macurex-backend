package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EmployeeDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;

@Repository
public interface EmployeeDetailsRepo extends JpaRepository<EmployeeDetailsVO, Long> {


//	
//	@Modifying
//	@Query("delete from EmployeeDetailsVO p where p.employeeMasterVO = :vo")
//	int deleteByEmployeeMasterVO(@Param("vo") EmployeeMasterVO vo);

	EmployeeDetailsVO findByEmployeeMasterVO(EmployeeMasterVO saved);

}
