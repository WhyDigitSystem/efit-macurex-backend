package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrderShortCloseDetailsDTO {

	private Long itemId;

	private BigDecimal orderQty;

	private BigDecimal suppliedQty;

	private BigDecimal requiredQty;

}
