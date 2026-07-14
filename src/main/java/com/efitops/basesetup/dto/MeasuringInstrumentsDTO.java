package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasuringInstrumentsDTO {

	private Long id;
	private String instrumentName;
	private String instrumentCode;
	private BigDecimal ranges;
	private BigDecimal leastCount;
	private String colourCode;
	private BigDecimal calibrationFrequence;
	private String remarks;	
	private String createdBy;
	private String instrumentDesc;

	private Long orgId;
	private String branch;
	private String branchCode;
    private String finYear;
}
