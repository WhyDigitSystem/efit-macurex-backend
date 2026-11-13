package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ActivityMasterVO;

public interface ActivityMasterRepo extends JpaRepository<ActivityMasterVO, Long> {

	List<ActivityMasterVO> findByOrgId(Long orgId);

}
