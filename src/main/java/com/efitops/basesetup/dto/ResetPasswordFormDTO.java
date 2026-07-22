package com.efitops.basesetup.dto;


import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordFormDTO {

//	@NotBlank(message = "Email is Required")
//	@Size(max = 30)
//	@Email
	private String userName;

	private String otp;
	
	@NotBlank
//	@Size(min = 6, max = 100, message = "New Password is required")
	private String newPassword;
}

