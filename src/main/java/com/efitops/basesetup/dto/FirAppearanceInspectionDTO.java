package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirAppearanceInspectionDTO {
	private String characteristics;
	private String methodOfInspection;
	private String specification;
	private BigDecimal lsl;
	private BigDecimal usl;
	private String observation;
	private String remarks;

}
