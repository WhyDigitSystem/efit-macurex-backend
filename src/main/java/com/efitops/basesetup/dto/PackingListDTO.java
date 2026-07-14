package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingListDTO {
	private Long id;
	private String customerName;
	private String customerAddress;
	private String salesOrderNo;
	private LocalDate salesOrderDate;
	private LocalDate supplyDate;
	private String deliveryPlace;
	private int noOfPackage;
	private String vendorCode;
	private Long orgId;
	private String createdBy;
	private String narration;
	
	private String branch;
	private String branchCode;
	private String finYear;


	
	List<PackingListDetailsDTO> packingListDetailsDTO;
}