package com.efitops.basesetup.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efitops.basesetup.entity.RolesPermissionHeaderVO;
import com.efitops.basesetup.entity.RolesPermissionVO;

@Repository
public interface RolePermissionRepo extends JpaRepository<RolesPermissionVO, Long> {

	List<RolesPermissionVO> findByRolesPermissionHeaderVO(RolesPermissionHeaderVO vo);


}