package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PurchaseContractAmendmentDto {

	private Long id;

	private Long branch;

	private String belongsTo;

	private Long customer;

	private String contractNo;
	private LocalDate contractDate;

	// Revision
	private int revisionNo;

	// Reference
	private String refNo;
	private LocalDate refDate;

	private Long orgId;

	private String createdBy;

	private boolean active;
	private boolean cancel;
	private String cancelRemarks;

	private String freightType;

	private String packingType;

	private BigDecimal insuranceAmount;

	private String modeOfDespatch;

	private String taxDescription;

	private String preparedBy;

	private String authorisedBy;

	private String remarks;

	private String financialYear;

	private List<PurchaseContractAmendmentDetailsDto> details;

}
