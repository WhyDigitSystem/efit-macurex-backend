package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailsDTO {

	private String employeeType;
	private String department;
	private LocalDate dateOfJoining;
	private String designation;
	private String country;

	private String payCategory;
	private String minimumWageCategory;
	private String ptState;
	private String jobLocation;
	private LocalDate dateOfLeaving;

}
