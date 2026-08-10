package com.efitops.basesetup.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespatchInstDetailsResponseDTO {
	private String ordAccpContrNo;
	private String date;
	private ItemResponse1DTO item;
	private ItemResponse1DTO unit;
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
