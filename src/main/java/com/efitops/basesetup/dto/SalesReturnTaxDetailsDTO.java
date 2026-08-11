package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReturnTaxDetailsDTO {
	  
	
       private Long id;

	   // Particulars
	   private Long particulars;

	   // Amount
	   private BigDecimal amount;

}
