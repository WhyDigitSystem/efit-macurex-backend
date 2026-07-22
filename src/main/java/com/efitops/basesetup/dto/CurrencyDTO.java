package com.efitops.basesetup.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrencyDTO {

	private Long id;
	private Long countryId;
	private String mainCurrency;
	private String cancelRemarks;
	private String currency;
	private String subCurrency;
	private String mainCurrencySymbol;
	private String subSymbol;
	private String currencyRepresentation;
	private String currencyInteger;
	private String currencyDecimal;
	private String currencyDescription;
	private Long orgId;
	private boolean active;
	private String createdBy;
}


