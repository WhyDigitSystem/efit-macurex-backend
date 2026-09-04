package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ReasonMasterVO;

public interface ReasonMasterRepo extends JpaRepository<ReasonMasterVO, Long> {

	List<ReasonMasterVO> findByOrgId(Long orgId);

}
