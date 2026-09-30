package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SCBillTaxGridResponseDTO {
	
	
	    private Long id;

	    private String particulars;

	    private String glAccountName;

	    private BigDecimal acceptedQtyAmount;

	    private BigDecimal revisedAmount;


}
