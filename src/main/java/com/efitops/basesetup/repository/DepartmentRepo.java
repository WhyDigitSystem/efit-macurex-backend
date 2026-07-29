package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DepartmentVO;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentVO, Long> {

	@Query(nativeQuery = true, value = "select * from department  where org_id=?1 and branch=?2 and active=1 and cancel=0")
	List<DepartmentVO> getAllDepartmentByOrgId(Long orgId, Long branch);

	@Query(nativeQuery = true, value = "select * from department where departmentid=?1")
	List<DepartmentVO> getDepartmentById(Long id);

	boolean existsByDepartmentNameAndOrgId(String departmentName, Long orgId);

	boolean existsByDepartmentCodeAndOrgId(String departmentCode, Long orgId);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branch=?3 and screencode=?4")
	String getDepartmentDocId(Long orgId,String finYear,Long long1, String screenCode);

}


