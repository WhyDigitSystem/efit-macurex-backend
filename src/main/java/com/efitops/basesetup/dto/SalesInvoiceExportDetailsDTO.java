package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesInvoiceExportDetailsDTO {
	private String item;
	private String itemDesc;
	private String units;
	private BigDecimal qty;
	private BigDecimal rate;
	private BigDecimal discount;

}
