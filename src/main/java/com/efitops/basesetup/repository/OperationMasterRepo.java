package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.OperationMasterVO;

public interface OperationMasterRepo extends JpaRepository<OperationMasterVO, Long> {

	@Query("SELECT om FROM OperationMasterVO om WHERE om.orgId = ?1 " +
		       "AND om.cancel = false AND om.active = true")
		List<OperationMasterVO> getOperationMasterByOrgId(Long orgId);

}
