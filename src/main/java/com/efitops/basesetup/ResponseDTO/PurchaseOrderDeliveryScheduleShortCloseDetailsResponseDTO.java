package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDeliveryScheduleShortCloseDetailsResponseDTO {

	private Long id;

	private ItemMasterDetailsResponseDTO item;

	private UnitMasterResponseDTO unit;

	private BigDecimal orderedQty;

	private BigDecimal suppliedQty;

	private BigDecimal pendingQty;

	private BigDecimal newRequiredQty;

	private BigDecimal shortCloseQty;
}
