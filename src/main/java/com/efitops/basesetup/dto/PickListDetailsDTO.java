package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickListDetailsDTO {


	private String item;

	private String itemName;

	private String unit;

	private String rackNo;

	private BigDecimal rackQty;

	private BigDecimal issuedQty;

	private BigDecimal pickedQty;

	private BigDecimal actualQty  ;

	private BigDecimal remainingQty;
	
	private boolean flag;
}
