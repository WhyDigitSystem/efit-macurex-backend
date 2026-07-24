package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePersonalDetailsDTO {

	private String birthPlace;
	private String religion;
	private String passportNo;
	private String homeState;
	private String nationality;
	private LocalDate expiryDate;
	private String countryOfOrigin;
	private String placeOfIssue;
}
 