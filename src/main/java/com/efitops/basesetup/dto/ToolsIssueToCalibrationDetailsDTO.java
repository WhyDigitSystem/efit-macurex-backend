package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolsIssueToCalibrationDetailsDTO {
	
	private String instrumentId;
	private String instrumentName;
	private String instrumentDesc;
	private BigDecimal issQty;
	private String unit;
	
	
}
