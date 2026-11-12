package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponseEntryDetailsResponseDTO {
	
	private Long item;

	private BigDecimal qty;

	private BigDecimal responseQty;

	private String reason;

}
