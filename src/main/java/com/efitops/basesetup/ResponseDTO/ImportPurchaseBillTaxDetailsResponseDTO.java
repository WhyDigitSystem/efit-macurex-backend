package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class ImportPurchaseBillTaxDetailsResponseDTO {
	private Long id;
	private String particulars;
	private BigDecimal tax;
	private BigDecimal taxval1;
	private BigDecimal taxAmount;
	private String dbCr;
	private String glSubledger;
}