package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.EmployeeLoanDetailsVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;

@Repository
public interface EmployeeLoanDetailsRepo extends JpaRepository<EmployeeLoanDetailsVO, Long>{


	@Modifying
	@Query("delete from EmployeeLoanDetailsVO l where l.employeeMasterVO = :vo")
	int deleteByEmployeeMasterVO(@Param("vo") EmployeeMasterVO vo);

	List<EmployeeLoanDetailsVO> findByEmployeeMasterVO(EmployeeMasterVO saved);
}
