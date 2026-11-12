package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespatchInstDetailsResponseDTO {
	private String ordAccpContrNo;
	private LocalDate date;
	private ItemResponse1DTO item;
	private String pdi;
	private LocalDate pdiDate;
	private  String schduleMonth;
	private BigDecimal plannedQty;
	private BigDecimal pendingQty;
	private BigDecimal availableQty;
	private BigDecimal descQty;
	private String noOfPackage;
	private String packageType;
}
