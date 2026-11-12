package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

	 private Long id;
	    private String userName;
	    private String nickName;

	    private Long employeeId;
	    private String employeeCode;
	    private String employeeName;
	    private String email;
	    private String mobileNo;

	    private Long companyId;
	    private String companyName;

	    private Long orgId;

	    private String userType;
	    private String customer;
	    private String client;

	    private boolean loginStatus;
	    private boolean active;

	    private String createdby;
	    private String updatedby;

	    private List<UserLoginRolesResponseDTO> roleAccess;

	    private List<UserLoginBranchAccessResponseDTO> branchAccess;
	    
}
