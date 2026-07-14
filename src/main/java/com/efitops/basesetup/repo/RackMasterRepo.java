package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RackMasterVO;

@Repository
public interface RackMasterRepo extends JpaRepository<RackMasterVO, Long>{

	@Query(nativeQuery = true,value = "Select * from rackmaster where rackmasterid=?1")
	List<RackMasterVO> getRackMasterById(Long id);

	@Query(nativeQuery = true,value = "Select * from rackmaster where orgid=?1")
	List<RackMasterVO> getRackMasterByOrgId(Long orgId);

}
