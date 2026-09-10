package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportPurchaseBillDetailsDTO {

	private Long item;

	private BigDecimal challanQty;

	private BigDecimal grnQty;

	private BigDecimal accptQty;

	private BigDecimal shortageQty;

	private BigDecimal fobRateFc;

	private BigDecimal fobValueFc;

	private BigDecimal fobValueInr;

	private BigDecimal dutyAmtInr;

	private BigDecimal valueFc;

	private BigDecimal valueInr;

	private BigDecimal landCostInr;

}
