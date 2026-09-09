package com.efitops.basesetup.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponseEntryDTO {

	private Long id;

	private String complaintNo;

	private String complaintDate;

	private String productNo;

	private String productName;

	private String supplierNo;

	private String supplierName;

	private boolean active;

	private Long orgId;

	private String createdBy;

	private String FinancialYear;

	private String cancelRemarks;
	
	private String remarks;
	
	private List<SupplierResponseEntryDetailsDTO> supplierResponseEntryDetailsDTO;

}
