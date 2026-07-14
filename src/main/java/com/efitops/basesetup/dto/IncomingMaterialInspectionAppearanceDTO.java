package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomingMaterialInspectionAppearanceDTO {
	private String characteristics;
	private String methodOfInspection;
	private String specifications;
}
