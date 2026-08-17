package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialYearDTO {

	private Long id;
	private int finYear;
	private LocalDate startDate;
	private LocalDate endDate;
	private Long orgId;
	private String createdBy;
	private boolean active;
	
	private String cancelRemarks;

}


