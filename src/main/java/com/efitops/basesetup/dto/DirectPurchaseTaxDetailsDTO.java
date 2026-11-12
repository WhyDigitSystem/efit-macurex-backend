package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectPurchaseTaxDetailsDTO {

	private String particulars;

	private BigDecimal tax;

	private BigDecimal acceptedQtyAmount;

	private BigDecimal revisedAmount;

	private String taxId;
}