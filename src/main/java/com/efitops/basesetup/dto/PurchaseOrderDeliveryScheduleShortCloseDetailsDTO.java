package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import com.efitops.basesetup.entity.ItemMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDeliveryScheduleShortCloseDetailsDTO {

	private Long item;

	private Long unit;

	private BigDecimal orderedQty;

	private BigDecimal suppliedQty;

	private BigDecimal pendingQty;

	private BigDecimal shortCloseQty;

}
