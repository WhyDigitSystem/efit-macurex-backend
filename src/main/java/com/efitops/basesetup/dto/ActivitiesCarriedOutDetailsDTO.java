package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivitiesCarriedOutDetailsDTO {
	
	private String scheduledActivity;

	private Long item;
	
	private LocalTime fromTime;
	
	private LocalTime toTime;
	
	private String checkingPoints;

	private String parameter;
	
	private String activitiesCarriedOut;
	
	private String status;

	private LocalDate date;
	
	private String nextActivity;

	private BigDecimal NoOfHrs;
	
	private String frequency;
	
	private LocalDate nextScheduleDate;

}
