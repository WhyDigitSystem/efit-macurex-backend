package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderDetailsDTO {
	private String timeInHours;
	private String unit;
	private int hoursProduction;
	private int rework;
	private int reject;
	private int idealTime;
	private String remarks;
}
