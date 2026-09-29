package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivitiesCarriedOutDetailsResponseDTO {
	
	private String scheduledActivity;

	private ItemResponse1DTO item;
	
	private String  fromTime;
	
	private String toTime;
	
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
