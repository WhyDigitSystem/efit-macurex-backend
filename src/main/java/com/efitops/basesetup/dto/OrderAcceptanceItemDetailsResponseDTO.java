package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAcceptanceItemDetailsResponseDTO {
	private Long itemId;

	private String itemCode;

	private String itemDescitpion;
	private Long orderId;
	private BigDecimal quantity;
	

}
