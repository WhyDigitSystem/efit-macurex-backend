package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorComplaintEntryResponseDTO {
	
	private Long id;

	private String docId;

	private LocalDate docDate;
	
	private String financialYear;

	private ItemResponse1DTO fgItem;

	private Long supplier;

	private boolean active;

	private Long orgId;

	private String createdBy;
	
	private String cancelRemarks;
	
	private String remarks;
	
	private List<VendorComplaintDetailsResponseDTO> vendorComplaintDetailsResponseDTO;

}
