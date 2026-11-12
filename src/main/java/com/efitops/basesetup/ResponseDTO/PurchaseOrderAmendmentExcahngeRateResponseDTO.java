package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderAmendmentExcahngeRateResponseDTO {
	
	private Long id;
	
	private Double sellingExRate;
	
	 private Double buyingExRate;

}
