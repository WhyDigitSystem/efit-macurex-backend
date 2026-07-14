package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionPlanDetailsDTO {

	private String process;
	private int qty;
	private String fromDate;
	private String toDate;
	private String machineName;
	private String machineNo;
	private String timeTakenInSec;
	private String totalTimeTaken;
	private String timeTakenInHours;
	private String qtyPerHr;
	private String expMinProd;
	private String expMaxProd;
	private String status;
}