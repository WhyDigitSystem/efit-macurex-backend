package com.efitops.basesetup.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.efitops.basesetup.dto.ChangePasswordFormDTO;
import com.efitops.basesetup.dto.LoginFormDTO;
import com.efitops.basesetup.dto.RefreshTokenDTO;
import com.efitops.basesetup.dto.ResetPasswordFormDTO;
import com.efitops.basesetup.dto.ResponsibilityDTO;
import com.efitops.basesetup.dto.RolesDTO;
import com.efitops.basesetup.dto.SignUpFormDTO;
import com.efitops.basesetup.dto.UserResponseDTO;
import com.efitops.basesetup.entity.ResponsibilityVO;
import com.efitops.basesetup.entity.RolesVO;
import com.efitops.basesetup.entity.UserVO;
import com.efitops.basesetup.exception.ApplicationException;

@Service
public interface AuthService {

	public void signup(SignUpFormDTO signUpRequest) throws ApplicationException;

	public UserResponseDTO login(LoginFormDTO loginRequest, HttpServletRequest request) throws ApplicationException;

	public void logout(String userName);

	public void changePassword(ChangePasswordFormDTO changePasswordRequest);

	public void resetPassword(ResetPasswordFormDTO resetPasswordRequest);

	public RefreshTokenDTO getRefreshToken(String userName, String tokenId) throws ApplicationException;
	
	List<Map<String, Object>> getResponsibilityForRolesByOrgId(Long orgId);
	
	Map<String, Object> createUpdateRoles(RolesDTO rolesDTO) throws ApplicationException;
	
	public List<RolesVO> getAllRoles(Long orgId);

	public List<RolesVO> getAllActiveRoles(Long orgId);
	
	RolesVO getRolesById(Long id) throws ApplicationException;
	
	Map<String, Object> createUpdateResponsibilities(ResponsibilityDTO responsibilityDTO) throws ApplicationException;
	
	public List<ResponsibilityVO> getAllResponsibility(Long orgId);

	public List<ResponsibilityVO> getAllActiveResponsibility(Long orgId);
	
	ResponsibilityVO getResponsibilityById(Long id) throws ApplicationException;
	
	List<UserVO>getAllUsersByOrgId(Long orgId);
	
	public UserVO getUserById(Long userId);

	public UserVO getUserByUserName(String userName);

	public Map<String, Object> sendOtp(String userName);

	public void resetPasswordNew(ResetPasswordFormDTO dto);


	

}
