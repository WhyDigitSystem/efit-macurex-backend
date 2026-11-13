package com.efitops.basesetup.ResponseDTO;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemMasterDetailsResponseDTO {
	private Long id;
	private String itemCode;
	private String itemDescription;
	private String hsnCode;
	private String customerPoNo;
	private UnitMasterResponseDTO unit;
	private UnitMasterResponseDTO purchaseUnit;
	private UnitMasterResponseDTO primaryUnit;
}
