package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NcProductRegisterDetailsDTO {

	private Long id;

	private LocalDate date;
	private String stage;
	private String partNo;
	private String partDescription;
	private String processDescription;
	private String detailsOfNonConformance;

	private BigDecimal ncQuantity;
	private BigDecimal unit;

	private String correctiveaction;
	private String capaRef;
	private String signature;
	private String remarks;

}