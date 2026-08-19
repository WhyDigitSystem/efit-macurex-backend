package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderImportDetailsDTO {

	private String indentNo;

	private String indentDate;

	private Long item;

	private String hsnCode;

	private Long uom;

	private BigDecimal indentQty;

	private BigDecimal fobRateFc;

	private BigDecimal fobRateInr;

	private BigDecimal fobValueInr;
	
	private BigDecimal orderRate;

}
