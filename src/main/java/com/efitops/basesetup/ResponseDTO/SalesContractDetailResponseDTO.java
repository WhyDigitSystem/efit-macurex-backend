package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractDetailResponseDTO {
	private Long id;
	private ItemResponse1DTO item;
	private Double oldRate;
	private Double newRate;
	private String validFrom;
	private String validTo;
	private String newValidDate;

}
