package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.ItemMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineToolsScrapNoteDetailsResponseDTO {
	
	private ItemResponse1DTO item;

	private BigDecimal stock;

	private BigDecimal quantity;

	private BigDecimal rate;

	private BigDecimal value;

}
