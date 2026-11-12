package com.efitops.basesetup.dto;

import java.time.LocalTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUpApprovalDetailsDTO {

	private String operationNo;

	private String description;

	private String Specification;

	private String details1;

	private String details2;

	private String details3;

	private String details4;

	private String details5;

	private String details6;

	private String details7;

	private String details8;

	private String details9;

	private String details10;

	private LocalTime time;

	private String remarks;

}
