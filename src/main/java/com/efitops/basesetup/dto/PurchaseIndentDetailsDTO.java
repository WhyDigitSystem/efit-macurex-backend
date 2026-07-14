package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentDetailsDTO {

	
	private String item;
	private String itemDescription;
	private String uom;
	private BigDecimal reqQty;
	private BigDecimal avlStock;
	private BigDecimal indentQty;
	
}
