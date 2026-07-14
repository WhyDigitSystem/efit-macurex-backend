package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDetailsDTO {
	
	private String item;
	private String itemDesc;
	private String hsnSacCode;
	private String taxType;
	private String uom;
	private BigDecimal prevRate;
	private BigDecimal price; 
	private BigDecimal qty;
	private BigDecimal discount;
	private BigDecimal sgst;
	private BigDecimal cgst;
	private BigDecimal igst;

}
