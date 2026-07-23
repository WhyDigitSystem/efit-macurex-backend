package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.BranchVO;

@Repository
public interface BranchRepo extends JpaRepository<BranchVO, Long>{

	boolean existsByBranchNameAndOrgId(String branchName, Long orgId);

	boolean existsByBranchCodeAndOrgId(String branchCode, Long orgId);

}
