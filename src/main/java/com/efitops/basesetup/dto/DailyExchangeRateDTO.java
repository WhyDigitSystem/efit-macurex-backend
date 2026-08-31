package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyExchangeRateDTO {

	private Long id;

	private Long branch;

	private Long currency;

	private LocalDate effectiveFrom;

	private double sellingExRate;

	private double buyingExRate;
	private String month;
	private Long year;


	private Long orgId;
	private String createdBy;
	private String cancelRemarks;
	private boolean active;
}
