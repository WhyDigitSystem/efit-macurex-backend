package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EmployeeCommunicationDetailsVO;

@Repository
public interface EmployeeCommunicationDetailsRepo extends JpaRepository<EmployeeCommunicationDetailsVO, Long>{



//	@Modifying
//	@Query("delete from EmployeeCommunicationDetailsVO c where c.employeeMasterVO = :vo")
//	int deleteByEmployeeMasterVO(@Param("vo") EmployeeMasterVO vo);

	//	EmployeeCommunicationDetailsVO findByEmployeeMasterVO(EmployeeMasterVO saved);

	
}
