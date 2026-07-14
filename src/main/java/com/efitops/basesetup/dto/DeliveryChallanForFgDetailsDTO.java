package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChallanForFgDetailsDTO {
	private String itemNo;
	private String itemDescription;
	private BigDecimal quantity;
	private String unit;
	private BigDecimal weight;
	private String remarks;
	private String salesOrderNo;


}
