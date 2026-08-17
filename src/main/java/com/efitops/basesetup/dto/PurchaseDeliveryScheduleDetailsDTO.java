package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDeliveryScheduleDetailsDTO {

	private Long id;

	private Long item;
	private Long primaryUnit;
	private Long purchaseUnit;
	private BigDecimal demandQty;
	private BigDecimal availableStock;
	private BigDecimal qty;
	private BigDecimal tentativeQty;
	private BigDecimal tentativeQtyNextMonth;
	private BigDecimal rate;
	private List<PurchaseDeliveryScheduleLineDTO> schedule;
}