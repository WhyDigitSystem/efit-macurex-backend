package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsDTO {
	private Long id;
	private String bankName;
	private String ifscCode;
	private Long accountNo;
	private String bankBranch;
}