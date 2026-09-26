package com.efitops.basesetup.dto;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivitiesCarriedOutComponentDetailsDTO {

	private Long item;
	
	private BigDecimal reqQty;
	
	private BigDecimal rate;
	
	private BigDecimal amount;
	
	private String remarks;
}
