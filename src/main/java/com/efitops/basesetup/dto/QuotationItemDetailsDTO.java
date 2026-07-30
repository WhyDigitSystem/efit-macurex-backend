package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationItemDetailsDTO {

	private Long id;
	private Long itemCodeId;
	private Long itemDescriptionId;

	private Long unitId;

	private String taxName;

	private String taxCode;

	private BigDecimal qtyOffered;

	private BigDecimal minPrice;

	private BigDecimal enquiryPrice;

	private BigDecimal basicPrice;

	private BigDecimal discountPercentage;

	private BigDecimal discountAmount;

	private BigDecimal lastRate;
	private BigDecimal lRate;

	private BigDecimal quotationAmount;

	private BigDecimal edPercentage;

	private BigDecimal edValue;

	private BigDecimal eduPercentage;

	private BigDecimal eduVal;

	private BigDecimal vatPercentage;

	private BigDecimal vatValue;

	private BigDecimal quotRatePiece;

	private BigDecimal amount;

	private LocalDate deliveryDate;

	private Long currencyNameId;

	private String currencySymbol;

	private String enqDetailId;

	private String offerControl;

	private String enquiryItem;

}
