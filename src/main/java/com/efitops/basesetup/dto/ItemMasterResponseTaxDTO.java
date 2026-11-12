package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemMasterResponseTaxDTO {
	
	private Long id;

	private String itemCode;

	private String itemDescription;

	private String hsnCode;
	
	private String customerPartNo;

}
