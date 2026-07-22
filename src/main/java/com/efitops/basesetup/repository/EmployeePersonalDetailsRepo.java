package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.EmployeePersonalDetailsVO;

@Repository
public interface EmployeePersonalDetailsRepo extends JpaRepository< EmployeePersonalDetailsVO, Long>{



//	@Modifying
//	@Query("delete from EmployeePersonalDetailsVO p where p.employeeMasterVO = :vo")
//	int deleteByEmployeeMasterVO(@Param("vo") EmployeeMasterVO vo);

	EmployeePersonalDetailsVO findByEmployeeMasterVO(EmployeeMasterVO saved);

}
