package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivitiesCarriedOutComponentDetailsResponseDTO {

	private ItemResponse1DTO item;

	private BigDecimal reqQty;

	private BigDecimal rate;

	private BigDecimal amount;

	private String remarks;

}
