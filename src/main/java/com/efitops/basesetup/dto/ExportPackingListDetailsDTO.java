package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportPackingListDetailsDTO {

	private String partNo;
	private String partDesc;
	private String custpo;
	private String customerPoItem;
	private String hsnCode;
	private String poNo;
	private BigDecimal quantity;
	private BigDecimal poQuantity;
	private String unit;
	private BigDecimal weightKg;
	private String price;
	private String sano;
	private String wono1;
}
