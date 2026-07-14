package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesInvoiceLocalDetailsDTO {
	private String item;
	private String itemDesc;
	private String units;
	private int avlStkQty;
	private BigDecimal qty;
	private BigDecimal rate;
	private String taxCode;
	private BigDecimal discount;
	private BigDecimal sgst;
	private BigDecimal cgst;
	private BigDecimal igst;

}
