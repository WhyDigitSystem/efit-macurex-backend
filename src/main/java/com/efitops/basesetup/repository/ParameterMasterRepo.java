package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.ParameterMasterVO;

public interface ParameterMasterRepo extends JpaRepository<ParameterMasterVO, Long> {

	List<ParameterMasterVO> findByOrgId(Long orgId);

}
