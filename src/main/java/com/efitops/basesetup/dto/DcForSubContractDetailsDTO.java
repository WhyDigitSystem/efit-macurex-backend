package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DcForSubContractDetailsDTO {
	
	private String item;
	private String itemDesc;
	private String process;
	private String unit;
	private BigDecimal qty;
	private BigDecimal weight;
	private String remarks;

}
