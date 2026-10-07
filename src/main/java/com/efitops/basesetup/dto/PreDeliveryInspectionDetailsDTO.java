package com.efitops.basesetup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreDeliveryInspectionDetailsDTO {

	private String parameter;
	private String parameterType;
	private String specification;
	private String instrumentName;
	private Long unit;
	private String tol;
	private String method;
	private String obs1;
	private String obs2;
	private String obs3;
	private String obs4;
	private String obs5;
}