package com.efitops.basesetup.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractAmdDetailsDTO {
//	private Long id;
	private Long item;
	private double oldRate;
	private double newRate;
	private LocalDate validFrom;
	private LocalDate validTo;
	private LocalDate newValidDate;


}
