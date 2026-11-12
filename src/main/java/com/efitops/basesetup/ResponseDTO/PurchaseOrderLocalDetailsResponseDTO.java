package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderLocalDetailsResponseDTO {

	private Long id;

	private String indentNo;

	private String indentDate;

	private ItemMasterDetailsResponseDTO item;

	private UnitResponseDTO purchaseUnit;

	private UnitResponseDTO primaryUnit;

	private BigDecimal indentQty;

	private BigDecimal poQtyInPurchaseUnit;

	private BigDecimal qtyInPrimaryUnit;

	private BigDecimal rateInInr;

	private BigDecimal discount;

	private BigDecimal discountAmount;

	private BigDecimal amountInInr;

	private LocalDate deliveryDate;

	private BigDecimal taxPercentage;

//    private String hsnCode;

	private String taxType;

	private BigDecimal sgstRate;

	private BigDecimal sgstAmount;

	private BigDecimal cgstRate;

	private BigDecimal cgstAmount;

	private BigDecimal igstRate;

	private BigDecimal igstAmount;

}