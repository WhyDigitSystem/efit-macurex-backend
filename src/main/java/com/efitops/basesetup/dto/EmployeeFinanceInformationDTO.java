package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFinanceInformationDTO {

	private String modeOfPayment;
	private String accountNumber;
	private String ifscCode;
	private String bankName;
	private String bankBranchName;
	private String payBill;
	private LocalDate date;
}
