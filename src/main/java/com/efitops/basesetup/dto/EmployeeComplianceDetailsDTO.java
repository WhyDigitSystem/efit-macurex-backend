package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeComplianceDetailsDTO {

	private String esiNo;
	private String uanNo;
	private boolean pt;
	private String insuranceNumber;
	private String pfNumber;
	private boolean pf;
	private boolean esi;
	
}
