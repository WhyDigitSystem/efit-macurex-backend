package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EmployeeFinanceInformationVO;

@Repository
public interface EmployeeFinanceInformationRepo extends JpaRepository<EmployeeFinanceInformationVO, Long>{


//	@Modifying
//	@Query("delete from EmployeeFinanceInformationVO f where f.employeeMasterVO = :vo")
//	int deleteByEmployeeMasterVO(@Param("vo") EmployeeMasterVO vo);
//
//	List<EmployeeFinanceInformationVO> findByEmployeeMasterVO(EmployeeMasterVO saved);
}

