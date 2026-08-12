package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractDetailResponseDTO {
	private Long id;
	private ItemResponse1DTO item;
	private double oldRate;
	private double newRate;
	private LocalDate validFrom;
	private LocalDate validTo;
	private LocalDate newValidDate;

}
