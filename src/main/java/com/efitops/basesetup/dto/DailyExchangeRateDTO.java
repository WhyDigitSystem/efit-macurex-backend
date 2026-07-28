package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyExchangeRateDTO {

	private Long dailyExchangeRateId;

	private String currencySymbol;

	private String currencyName;

	private BigDecimal exchangeRate;

	private String exchangeFromDate;

	private String exchangeToDate;

	private Long orgId;
	private String createdBy;
	private String cancelRemarks;
	private boolean active;
	private String financialYear;
}
