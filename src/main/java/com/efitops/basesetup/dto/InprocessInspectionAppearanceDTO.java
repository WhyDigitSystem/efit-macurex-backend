package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InprocessInspectionAppearanceDTO {

	private String characteristics;
	private String methodOfInspection;
	private String specification;
	private String observation;
	private String remarks1;

}
