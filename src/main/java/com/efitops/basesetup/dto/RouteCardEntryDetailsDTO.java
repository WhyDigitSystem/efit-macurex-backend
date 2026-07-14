package com.efitops.basesetup.dto;

import java.time.LocalDate;

import com.efitops.basesetup.entity.RouteCardEntryDetailsVO;
import com.efitops.basesetup.entity.RouteCardEntryVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteCardEntryDetailsDTO {

	private String operationDesc;
	private String machineCenter;
	private LocalDate processStart;
	private LocalDate processEnd;
	private int acceptedQty;
	private int qtyRework;
	private String reject;
	private String optr;
	private String remarks;
}
