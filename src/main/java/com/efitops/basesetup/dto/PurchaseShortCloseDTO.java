package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseShortCloseDTO {
	private Long id;
	private String poNumber;
	private LocalDate poDate;
	private String customerName;
	private String customerCode;
	private String supplierName;
	private String supplierCode;
	private String contactPerson;
	private long mobileNo;
	private String email;
	private String city;
	private String state;
	private String country;
	private String address;
	private String remarks;
	private Long orgId;
	private String createdBy;
	private String branch;
	private String branchCode;
	private String finYear;
	List<PurchaseShortCloseDetailsDTO> purchaseShortCloseDetailsDTO;

}
