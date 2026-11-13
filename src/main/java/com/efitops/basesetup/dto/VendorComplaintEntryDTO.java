package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorComplaintEntryDTO {

	private Long id;

	private String docId;

	private LocalDate docDate;
	
	private String financialYear;

	private Long fgItem;

	private Long supplier;

	private boolean active;

	private Long orgId;

	private String createdBy;
	
	private String cancelRemarks;
	
	private String remarks;
	
	private List<VendorComplaintDetailsDTO> vendorComplaintDetailsDTO;

}
