package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierChangeRequestResponseDTO {

    private Long id;

    private BranchResponseDTO branch;
    
    private String docId;

	private LocalDate docDate;

    private CustomerResponse1DTO vendorCode;

    private String partNo;

    private String partDescription;

    private String supplierContact;

    private Long supplierPhoneNo;

    private String supplierEmailId;

    private EmployeeDropdownResponseDTO buyerName;

    private Long buyerPhoneNo;

    private String buyerEmailId;

    private EmployeeDropdownResponseDTO sourceTriggeredBy;

    private Long sourcePhoneNo;

    private String sourceEmailId;

    // Reason for Change

    private String capacityIssueWithExisitingSupplier;

    private String customerRequirementDemandIncreased;

    private String alternativeRMSourceorAdditionalRMSource;

    private String internalCapacityIssue;

    private String changeInSupplierBaseQualityIssueinExisitingSupplier;

    private String supplierCommercialIssue;

    private String customeApprovedSource;

    private String others;

    private String changeDescriptionInDetails;

    private String detailOfProposedProcessOfOutSourced;

    // Impact of Change

    private String qualityImprovement;

    private String reducedLeadTime;

    private String costReduction;

    private String increaseManufacturingEfficiency;

    private String othersPleaseSpecify;

    private String effectOfChanges;

    private String riskAssessment;

    private String proposedIntroductionImplementationDate;

    private String supplierEvaluationReport;

    private String reliabilityFunctionalReportFromTDC;

    private String customerApproval;

    private String onJobTrainingReportFromMfg;

    private String processAuditReport;

    private String supplierRegistrationFrom;

    private String ppapIsirRequired;

    private String changeRequestApproval;

    // Authorized Signatures

    private EmployeeDropdownResponseDTO signByPurchase;

    private String purchaseDisposition;

    private EmployeeDropdownResponseDTO signByTDC;

    private String tdcDisposition;

    private EmployeeDropdownResponseDTO signByProduction;

    private String productionDisposition;

    private EmployeeDropdownResponseDTO signByQuality;

    private String qualityDisposition;

    private String note;
    
    private String active;

	
	private Long orgId;
	
	
	private String financialYear;

	
	private String createdBy;
}
