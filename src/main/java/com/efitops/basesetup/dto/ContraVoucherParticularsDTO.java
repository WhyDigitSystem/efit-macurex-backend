package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContraVoucherParticularsDTO {

	private String accountsName;
	private String subledgerName;
	private String subLedgerCode;
	private BigDecimal debit;
	private BigDecimal credit;
	private String narration;
}
