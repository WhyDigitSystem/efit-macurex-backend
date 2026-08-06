package com.efitops.basesetup.dto;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespatchInstructionDetailsDTO {
	private Long id;
	private String ordAccpContrNo;
	private String date;
	private Long item;
	private Long unit;
	private String pdi;
	private String pdiDate;
	private  String schduleMonth;
	private String plannedQty;
	private String pendingQty;
	private String availableQty;
	private String descQty;
	private String noOfPackage;
	private String packageType;
	
}
