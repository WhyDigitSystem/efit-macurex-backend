package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrapMaterialReturnRejectionResponseDTO {

	private Long id;
	
	private BranchResponseDTO branch;
	
	private ListOfValuesDetailsResponseDTO entryFor;
	
	private CustomerResponse1DTO vendorId;
	
	private LocationMasterResponseDTO toLocation;
	
	private LocationMasterResponseDTO vendorLocation;
	
	private String entryType;
	
	private String docNo;
	
	private LocalDate documentDate ;
	
	private String approvalByQc;
	
	private String reasonForRejection;
	
	private String approvalByPurchase;
	
	private Long orgId;

	private String financialYear;

	private String active;

	private String cancelRemarks;

	private String createdBy;
	
	private List<ScrapMaterialReturnRejectionDetailsResponseDTO> scrapMaterialReturnRejectionDetailsResponseDTO;
}
