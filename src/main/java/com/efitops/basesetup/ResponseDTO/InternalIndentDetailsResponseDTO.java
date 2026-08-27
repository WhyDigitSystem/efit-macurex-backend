package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternalIndentDetailsResponseDTO {
	private ItemResponse1DTO item;

	private BigDecimal requiredQty;

	private String purpose;
	

}
