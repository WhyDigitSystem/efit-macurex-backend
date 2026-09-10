package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportPurchaseBillTaxDetailsDTO {


	    private String particulars;

	    private BigDecimal tax;

	    private BigDecimal taxval1;

	    private BigDecimal taxAmount;

	    private String dbCr;

	    private String glSubledger;
}
