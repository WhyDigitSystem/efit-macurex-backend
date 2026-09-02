package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationMasterConsumablesDetailsResponseDTO {

	private ItemResponse1DTO consumables;

	private BigDecimal quantity;

	private ListOfValuesDetailsResponseDTO type;

}
