package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionTransferSlipDetailsResponseDTO {

	private Long id;
	private ItemMasterDetailsResponseImportDTO item; 
	private BigDecimal stock;
	private BigDecimal bomQty;
	private BigDecimal inputQty;
	private BigDecimal rate;
	private BigDecimal value;
	private UnitResponseDTO primaryUnit;
	private String scrapId;
	private BigDecimal scrapQty;
	private BigDecimal scrapTotal;

}
