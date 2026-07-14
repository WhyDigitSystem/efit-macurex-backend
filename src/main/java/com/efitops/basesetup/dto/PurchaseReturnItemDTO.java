package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReturnItemDTO {
	private String itemCode;
	private String itemName;
	private String hsnSacCode;
	private String taxCode;
	private String primaryUnit;
	private BigDecimal poRate;
	private BigDecimal rejectQty;
	private BigDecimal unitPrice;
	private BigDecimal sgst;
	private BigDecimal cgst;
	private BigDecimal igst;
	private BigDecimal orderQty;
	private BigDecimal challanQty;

}
