package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.ServiceAccMasterVO;

public interface ServiceAccMasterRepo extends JpaRepository<ServiceAccMasterVO, Long> {

	boolean existsByServiceNameAndOrgId(String serviceName, Long orgId);

	@Query(nativeQuery = true, value = "select * from serviceaccmaster where org_id=?1 and branch_id=?2 and active=1 and cancel=0")
	List<ServiceAccMasterVO> findByOrgIdAndBranch(Long orgId, Long branchId);

}
