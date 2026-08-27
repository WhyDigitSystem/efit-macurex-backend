package com.efitops.basesetup.ResponseDTO;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemMasterDetailsResponseInwardDTO {
	private Long id;
	private String itemCode;
	private String itemDescription;
	private UnitMasterResponseDTO primaryUnit;
	private UnitMasterResponseDTO purchaseUnit;

}
