package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherSalesInvoiceTaxDetailsDTO {
	
	private String particulars;

	private BigDecimal acceptedQtyAmount;

	private BigDecimal revisedAmount;

}
