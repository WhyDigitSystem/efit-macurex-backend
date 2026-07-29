package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class HolidayMasterDetailsDTO {
		
	private LocalDate holidayDate;	
	private String day;	
	private String holidayType;
	private String remarks;
	private String compensatory;
	private LocalDate compensatoryDate;
	
	
	
	
	

}
