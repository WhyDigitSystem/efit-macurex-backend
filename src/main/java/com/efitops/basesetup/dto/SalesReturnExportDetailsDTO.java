package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReturnExportDetailsDTO {
	private String item;
	private String itemDesc;
	private String units;
	private BigDecimal qty;
	private BigDecimal discount;
	private BigDecimal rejectQty;
	private BigDecimal rate;
}
