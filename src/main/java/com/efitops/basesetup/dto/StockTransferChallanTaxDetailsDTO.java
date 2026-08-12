package com.efitops.basesetup.dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferChallanTaxDetailsDTO {
	private Long id;
	private Long particulars;
	 private BigDecimal acceptQtyAmount;
	 private BigDecimal revisedAmoount;
	 

}
