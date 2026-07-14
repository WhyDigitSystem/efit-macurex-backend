package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BomDetailsDTO {
	
	private String itemType;
	
	private String itemCode;
	
	private String itemDesc;
	
	private String uom;
	
	private BigDecimal qty;

}
