package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.DesignationVO;

@Repository
public interface DesignationRepo extends JpaRepository<DesignationVO, Long> {

	@Query(nativeQuery = true, value = "select * from designation where org_id=?1  ")
	List<DesignationVO> getDesignationByOrgId(Long orgId);

	@Query(nativeQuery = true, value = "select * from designation where designation_id=?1")
	List<DesignationVO> getDesignationById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,5,0)) AS docid from documenttypemappingdetails where orgid=?1 and finyear=?2 and branch=?3 and screencode=?4")
	String getDesignationDocId(Long orgId, String finYear, Long branch, String screenCode);

	boolean existsByDesignationAndDesignationCodeAndOrgId(String designation, String designationCode, Long orgId);

	boolean existsByDesignationAndOrgId(String designation, Long orgId);

	boolean existsByDesignationCodeAndOrgId(String designationCode, Long orgId);
}

