package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespatchInstructionDetailsDTO {
	private Long id;
	private String ordAccpContrNo;
	private String date;
	private Long item;
	private Long unit;
	private String pdi;
	private String pdiDate;
	private  String schduleMonth;
	private BigDecimal plannedQty;
	private BigDecimal pendingQty;
	private BigDecimal availableQty;
	private BigDecimal descQty;
	private String noOfPackage;
	private String packageType;
	
}
