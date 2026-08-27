package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectPurchaseCashDetailsDTO {

	private String itemCode;

	private String itemDescription;

	private String hsnCode;

	private String taxType;

	private BigDecimal tax;

	private Long unit;

	private BigDecimal dcQty;

	private BigDecimal receivedQty;

	private BigDecimal rate;

}
