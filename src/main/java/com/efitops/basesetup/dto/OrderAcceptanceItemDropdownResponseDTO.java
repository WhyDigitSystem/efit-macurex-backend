package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderAcceptanceItemDropdownResponseDTO {

	private Long itemId;

	private String itemCode;

	private String itemDescription;

	private String unitId;

	private BigDecimal minimumSellPrice;

	private String hsnCode;

	private BigDecimal rate;
	private BigDecimal cgst;
	private BigDecimal sgst;
	private BigDecimal igst;

	private Long unitMasterId;

	private Long gstRateMasterId;

}
