package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderLocalTaxDetailsDTO {

	private String particulars;

	private BigDecimal tax;

	private BigDecimal amount;

}
