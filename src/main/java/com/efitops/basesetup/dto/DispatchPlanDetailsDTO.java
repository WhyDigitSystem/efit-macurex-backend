package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchPlanDetailsDTO {

	private String item;
	private String itemDesc;
	private String unit;
	private BigDecimal orderQty;
	private BigDecimal deliveryQty;
	private String remarks;
}
