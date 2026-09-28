package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrapMaterialReturnRejectionDetailsResponseDTO {
	
	private Long id;

	private ItemResponse1DTO item;

	private UnitResponseDTO unit;

	private BigDecimal availableStock;

	private BigDecimal recQty;

	private BigDecimal costRate;

	private BigDecimal amount;

	private String note;

}
