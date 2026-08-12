package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespatchInstDetailsResponseDTO {
	private String ordAccpContrNo;
	private String date;
	private ItemResponse1DTO item;
	private ItemResponse1DTO unit;
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
