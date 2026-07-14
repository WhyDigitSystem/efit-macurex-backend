package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampleApprovalDetailsDTO {

	private String characteristics;
	private String specification;
	private String methodOfInspection;
	private BigDecimal lsl;
	private BigDecimal usl;
	private String simple1;
	private String simple2;
	private String simple3;
	private String simple4;
	private String simple5;
	private String operator1;
	private String operator2;
	private String operator3;
	private String operator4;
	private String operator5;
	private String status;

}
