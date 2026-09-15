package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyInspectionCumRejectionDetailsResponseDTO {
	
	private Long fgItem;

	private BigDecimal stock;

	private BigDecimal rate;

	private BigDecimal inspectionQty;

	private BigDecimal acceptedQty;

	private BigDecimal reworkQty;

	private BigDecimal rejectionQty;

	private BigDecimal scrapQty;

}
