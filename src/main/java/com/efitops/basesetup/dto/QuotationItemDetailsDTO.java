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

	private Long item;

	private BigDecimal qtyOffered;

	private BigDecimal basicPrice;

	private BigDecimal discountPercentage;

	private BigDecimal quotationAmount;
	
	private BigDecimal discountAmount;


	private int qty;

	private LocalDate deliveryDate;

	private Long currencyName;

}
