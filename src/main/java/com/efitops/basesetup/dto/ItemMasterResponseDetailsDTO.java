package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemMasterResponseDetailsDTO {
	private Long id;
	private String itemDescription;
	private String itemCode;

}
