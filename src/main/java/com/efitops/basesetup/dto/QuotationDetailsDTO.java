package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationDetailsDTO {
	private String partCode;
	private String partDescription;
	private String drawingNo;
	private String revisionNo;
	private String unit;
	private BigDecimal unitPrice;
	private BigDecimal qtyOffered;
	private BigDecimal discount;
	private LocalDate deliveryDate;
}