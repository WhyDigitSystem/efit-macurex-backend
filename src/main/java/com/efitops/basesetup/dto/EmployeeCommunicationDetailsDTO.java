package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCommunicationDetailsDTO {

	private String address;
	private String contactNumber;
	private String emailId;
	private String city;
	private String state;
	private String country;
}
