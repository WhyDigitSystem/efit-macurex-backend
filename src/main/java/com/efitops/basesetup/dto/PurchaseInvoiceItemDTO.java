package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseInvoiceItemDTO {
	private String itemCode;
	private String itemName;
	private String hsnSacCode;
	private String taxtype;
	private String primaryUnit;
	private BigDecimal poRate;
	private BigDecimal receivedQty;
	private BigDecimal acceptQty;
	private BigDecimal unitPrice;
	private BigDecimal orderQty;
	private BigDecimal challanQty;
	private BigDecimal sgst;
	private BigDecimal cgst;
	private BigDecimal igst;
	private Long poDetailsId;

}
