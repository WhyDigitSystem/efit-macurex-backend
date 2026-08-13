package com.efitops.basesetup.dto;


import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor



public class PurchaseContractAmendmentDto {
	
	 private Long id;

	    // Plant
	    private Long branch;

	    // Belongs To
	    private String belongsTo;

	    // Amendment
	    private String docId;
	    private LocalDate docDate;

	    // Party
	    private Long party;
	    private String partyName;

	    // Purchase Contract
	    private String contractNo;
	    private LocalDate contractDate;

	    // Revision
	    private int revisionNo;

	    // Reference
	    private String refNo;
	    private LocalDate refDate;

	    // Organization
	    private Long orgId;

	    // Audit
	    private String createdBy;
	    private String updatedBy;

	    // Status
	    private boolean active;
	    private boolean cancel;
	    private String cancelRemarks;
	    
	    
	    private String freightType;

	    private String packingType;

	    private String insuranceAmount;

	    private String modeOfDespatch;

	    private String taxDescription;

	    private String preparedBy;

	    private String authorisedBy;

	    private String remarks;
	    
	    
	    private List<PurchaseContractAmendmentDetailsDto> details;


}
