package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.efitops.basesetup.entity.RolesVO;

public interface RolesRepo extends JpaRepository<com.efitops.basesetup.entity.RolesVO, Long> {

	boolean existsByRoleAndOrgId(String role, Long orgId);

	@Query(value = "select a from RolesVO a where a.active=true and a.orgId=?1")
	List<RolesVO> findAllActiveRolesByOrgId(Long orgId);

	@Query(value = "select a from RolesVO a where  a.orgId=?1")
	List<RolesVO> findAllRolesByOrgId(Long orgId);

}
