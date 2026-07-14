package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseShortCloseDetailsDTO {
	private String item;
	private String itemDesc;
	private String uom;
	private BigDecimal receivedQty;
	private BigDecimal shortageQty;
	private BigDecimal rate;
	private BigDecimal qty;

}
