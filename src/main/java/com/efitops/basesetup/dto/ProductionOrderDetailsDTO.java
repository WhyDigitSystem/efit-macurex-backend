package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionOrderDetailsDTO {
	private Long id;
	private String scheduleOrderNo;
	private LocalDate scheduleDate;
	private BigDecimal scheduleOrderQty;
	private BigDecimal balanceQty;
	private BigDecimal newReqQty;
	private BigDecimal shortClosedQty;
	private String reason;
}
