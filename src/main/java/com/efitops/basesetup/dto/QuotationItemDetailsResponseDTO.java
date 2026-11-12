package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.ResponseDTO.UnitResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationItemDetailsResponseDTO {

	private Long id;
	private ItemMasterResponseDetailsDTO itemCodes;

	private BigDecimal qtyOffered;


	private BigDecimal basicPrice;

	private BigDecimal discountPercentage;

	private BigDecimal discountAmount;

	private BigDecimal quotationAmount;

	private LocalDate deliveryDate;

	private CurrencyResponseDTO currency;


}
