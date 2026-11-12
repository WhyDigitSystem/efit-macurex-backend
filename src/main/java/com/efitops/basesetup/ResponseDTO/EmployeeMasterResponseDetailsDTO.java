package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeMasterResponseDetailsDTO {
	private Long id;
	private  String employeeName;
	private  String employeeCode;
	
}

