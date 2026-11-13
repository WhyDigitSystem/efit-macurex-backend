package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrderShortCloseDetailsResponseDTO {

	private Long id;

	private ItemMasterResponseGstDetailsDTO item;

	private BigDecimal orderQty;

	private BigDecimal suppliedQty;

	private BigDecimal pendingQty;

	private BigDecimal requiredQty;

	private BigDecimal shortCloseQty;

}
