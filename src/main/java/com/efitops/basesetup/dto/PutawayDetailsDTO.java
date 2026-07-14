package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutawayDetailsDTO {

	private String item;
	private String itemDesc;
	private String unit;
	private BigDecimal recQty;
	private BigDecimal putawayQty;
	private String rackNo;
}
