package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.EmployeeAttachmentVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;

public interface EmployeeAttachmentRepo extends JpaRepository<EmployeeAttachmentVO, Long>  {

	List<EmployeeAttachmentVO> findByemployeeMasterVO(EmployeeMasterVO employeeMasterVO);

}
