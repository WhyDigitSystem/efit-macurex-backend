package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseQuotationDetailsDTO {

	private String item;
	private String itemDesc;
	private String unit;
	private BigDecimal qty;
	private BigDecimal unitPrice;
//	private BigDecimal basicPrice;
	private BigDecimal discount;
//	private BigDecimal discountAmount;
//	private BigDecimal quoteAmount;

}
