package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import javax.persistence.Column;

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

	private Double sellingExRate;

	private Double buyingExRate;
    private Month month;
	private Year year;


	private Long orgId;
	private String createdBy;
	private String cancelRemarks;
	private boolean active;
	private String financialYear;
}
