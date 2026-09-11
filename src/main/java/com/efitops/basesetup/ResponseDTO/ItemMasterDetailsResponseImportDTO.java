package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemMasterDetailsResponseImportDTO {
	private Long id;
	private String itemCode;
	private String itemDescription;

}
