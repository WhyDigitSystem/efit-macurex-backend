package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrapMaterialReturnRejectionDTO {
	
	private Long id;
	
	private Long branch;
	
	private Long entryFor;
	
	private Long vendorId;
	
	private Long toLocation;
	
	private Long vendorLocation;
	
	private String entryType;
	
	private String docNo;
	
	private LocalDate documentDate ;
	
	private String approvalByQc;
	
	private String reasonForRejection;
	
	private String approvalByPurchase;
	
	private Long orgId;

	private String financialYear;

	private boolean active;

	private String cancelRemarks;

	private String createdBy;
	
	private List<ScrapMaterialReturnRejectionDetailsDTO> scrapMaterialReturnRejectionDetailsDTO;

}
