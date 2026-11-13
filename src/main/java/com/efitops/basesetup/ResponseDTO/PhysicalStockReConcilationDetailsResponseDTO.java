package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalStockReConcilationDetailsResponseDTO {
	
	private ItemResponse1DTO item;

	private BigDecimal bookStock;

	private BigDecimal actualQty;

	private BigDecimal difference;

	private BigDecimal lcRate;

	private BigDecimal rate;

	private String reasonCode;

	private BigDecimal amount;


}
