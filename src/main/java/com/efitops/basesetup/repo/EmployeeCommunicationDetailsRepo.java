package com.efitops.basesetup.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EmployeeCommunicationDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;

@Repository
public interface EmployeeCommunicationDetailsRepo extends JpaRepository<EmployeeCommunicationDetailsVO, Long>{



//	@Modifying
//	@Query("delete from EmployeeCommunicationDetailsVO c where c.employeeMasterVO = :vo")
//	int deleteByEmployeeMasterVO(@Param("vo") EmployeeMasterVO vo);

	EmployeeCommunicationDetailsVO findByEmployeeMasterVO(EmployeeMasterVO saved);

	
}
