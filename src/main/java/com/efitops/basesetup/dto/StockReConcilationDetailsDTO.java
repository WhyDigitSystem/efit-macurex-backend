package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockReConcilationDetailsDTO {
	private String itemCode;
	private String itemDesc;
	private String unit;
	private BigDecimal bookstock;
	private BigDecimal actualQty;
//	private BigDecimal lcRate;
	private BigDecimal rate;

}
