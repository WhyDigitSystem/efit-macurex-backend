package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderLocalDetailsDTO {

	private Long id;

	private String indentNo;

	private String indentDate;

	private Long item;

	private String customerPartNo;

	private Long purchaseUnit;

	private Long primaryUnit;

	private BigDecimal indentQty;

	private BigDecimal pendingIndentQty;

	private BigDecimal poQtyInPurchaseUnit;

	private BigDecimal qtyInPrimaryUnit;

	private BigDecimal rateInInr;

	private BigDecimal discount;

	private BigDecimal amountInInr;

	private LocalDate deliveryDate;

	private BigDecimal taxPercentage;

	private String hsnCode;

	private String taxType;


}
