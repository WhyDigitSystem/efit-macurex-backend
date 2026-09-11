package com.efitops.basesetup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.SetUpApprovalVO;

public interface SetUpApprovalRepo extends JpaRepository<SetUpApprovalVO, Long>{

	String getSetUpApprovalDocId(Long orgId, String financialYear, String screenCode);

}
