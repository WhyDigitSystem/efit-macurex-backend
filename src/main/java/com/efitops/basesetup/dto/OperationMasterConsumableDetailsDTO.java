package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationMasterConsumableDetailsDTO {
	
	private Long consumables;
	
	private BigDecimal quantity;
	
	private Long type;

}
