 package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account1DTO {
	private Long Id;
	private String balanceSheet;
	private String cashFlowStatement;
	private String incomeStatement;
}