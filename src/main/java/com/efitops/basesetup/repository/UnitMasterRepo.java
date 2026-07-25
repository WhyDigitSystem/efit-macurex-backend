package com.efitops.basesetup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efitops.basesetup.entity.UnitMasterVO;

public interface UnitMasterRepo extends JpaRepository<UnitMasterVO, Long> {

	boolean existsByOrgIdAndUnitIdIgnoreCase(Long orgId, String unitId);

	List<UnitMasterVO> findByOrgId(Long orgId);

}
