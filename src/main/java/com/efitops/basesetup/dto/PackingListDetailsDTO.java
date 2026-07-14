package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingListDetailsDTO {
	private String partNo;
	private String partDesc;
	private BigDecimal qty;
	private String unit;
	private BigDecimal weight;
	private String remarks;
	
	private String salesOrderNo;
	private BigDecimal poQty;
}
