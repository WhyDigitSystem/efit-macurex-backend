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
public class PurchaseContractAmendmentResponseDto {
	
	
	private Long id;
	private BranchResponseDTO branch;

	private String belongsTo;

	private String docId;
	private LocalDate docDate;

	private PurchaseContractAmendmentCustomerResponceDto customer;
	private String contractNo;
	private LocalDate contractDate;

	private int revisionNo;

	private String refNo;
	private LocalDate refDate;

	private String freightType;
	private String packingType;
	private double insuranceAmount;
	private String modeOfDespatch;
	private String taxDescription;
	private String preparedBy;
	private String authorisedBy;
	private String remarks;

	private Long orgId;
	private String createdBy;
	private String updatedBy;
	private String cancelRemarks;
	private boolean active;
	private boolean cancel;

	private String screenName;
	private String screenCode;
	
	private List<PurchaseContractAmendmentDetailsResponseDto> details;
	
	
	private List<PurchaseContractAmendmentAttachmentResponseDto> attachments;




}
