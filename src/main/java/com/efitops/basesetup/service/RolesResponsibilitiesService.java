package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.RolesPermissionHeaderDTO;
import com.efitops.basesetup.entity.RolesPermissionHeaderVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface RolesResponsibilitiesService {

	List<RolesPermissionHeaderVO> getRolesPermissionHeaderByRoleandOrgid(String role, Long orgid);

	Map<String, Object> createUpdateRoleScreenPermission(RolesPermissionHeaderDTO rolesPermissionHeaderDTO) throws ApplicationException;

}
