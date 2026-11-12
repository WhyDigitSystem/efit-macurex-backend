package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SCBillTaxGridDTO {
	
	
	    private Long id;

	    private String particulars;

	    private String glAccountName;

	    private BigDecimal acceptedQtyAmount;

	    private BigDecimal revisedAmount;

}
