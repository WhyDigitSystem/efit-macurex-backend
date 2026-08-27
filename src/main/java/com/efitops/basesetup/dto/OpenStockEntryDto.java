package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OpenStockEntryDto {
	
	 private Long id;
	
	private Long branch;
	
	private LocalDate asOnDate;
	
	private Long location;
	
	private Long item;
	
	private BigDecimal qty;

	private BigDecimal rate;
	
	private BigDecimal amount;

	private String remarks;
	
	private boolean active;
	
	private Long orgId;

	 private String createdBy;
	 
	 private String cancelRemarks;
}
