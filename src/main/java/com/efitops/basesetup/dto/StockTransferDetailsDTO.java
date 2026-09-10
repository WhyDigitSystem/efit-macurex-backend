package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferDetailsDTO {

	private Long item;

	private BigDecimal availableQty;

	private BigDecimal qty;

	private BigDecimal rate;

	private Long unit;
}
