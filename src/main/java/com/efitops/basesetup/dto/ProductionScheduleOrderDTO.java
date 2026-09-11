package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionScheduleOrderDTO {
	private Long id;

	private String orderType;

	private String lcPoNo;

	private LocalDate lcPoDate;

	private Long fgItem;

	private Long compRouteNo;

	private BigDecimal batchQty;

	private BigDecimal totalQty;

	private String shortClose;

	private Long bom;

	private LocalDate scheduleStartDate;

	private LocalDate scheduleEndDate;

	// Common fields

	private String createdBy;

	private boolean active;

	private String cancelRemarks;

	private Long orgId;

	private String financialYear;

	private Long branch;

	private List<ProductionScheduleOrderDetailsDTO> productionScheduleOrderDetailsDTO;
	private List<ScheduleDetailsDTO> scheduleDetailsDTO;
}