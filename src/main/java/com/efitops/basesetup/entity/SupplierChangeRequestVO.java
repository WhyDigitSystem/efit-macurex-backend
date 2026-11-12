package com.efitops.basesetup.entity;

import java.beans.JavaBean;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supplier_change_request_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierChangeRequestVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_change_request_basicgen")
	@SequenceGenerator(name = "supplier_change_request_basicgen", sequenceName = "supplier_change_request_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "supplier_change_request_basic_id")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@ManyToOne
	@JoinColumn(name = "vendor_code")
	private CustomerVO vendorCode;

	@Column(name = "part_no")
	private String partNo;

	@Column(name = "part_description")
	private String partDescription;

	@Column(name = "supplier_contact")
	private String supplierContact;

	@Column(name = "supplier_phone_no")
	private Long supplierPhoneNo;

	@Column(name = "supplier_email_id")
	private String supplierEmailId;

	@ManyToOne
	@JoinColumn(name = "buyer_name")
	private EmployeeMasterVO buyerName;

	@Column(name = "buyer_phone_no")
	private Long buyerPhoneNo;

	@Column(name = "buyer_email_id")
	private String buyerEmailId;

	@ManyToOne
	@JoinColumn(name = "source_triggered_by")
	private EmployeeMasterVO sourceTriggeredBy;

	@Column(name = "source_phone_no")
	private Long sourcePhoneNo;

	@Column(name = "source_email_id")
	private String sourceEmailId;

//	reason for change

	@Column(name = "capacity_issue_with_exisiting_supplier")
	private String capacityIssueWithExisitingSupplier;

	@Column(name = "customer_requirement_demand_increased")
	private String customerRequirementDemandIncreased;

	@Column(name = "alternative_rm_source_or_additional_rm_source")
	private String alternativeRMSourceorAdditionalRMSource;

	@Column(name = "internal_capacity_issue")
	private String internalCapacityIssue;

	@Column(name = "change_in_supplier_base_quality_issue_in_exisiting_supplier")
	private String changeInSupplierBaseQualityIssueinExisitingSupplier;

	@Column(name = "supplier_commercial_issue")
	private String supplierCommercialIssue;

	@Column(name = "customer_approved_source")
	private String customeApprovedSource;

	@Column(name = "others")
	private String others;

	@Column(name = "change_description_in_details")
	private String changeDescriptionInDetails;

	@Column(name = "detail_of_proposed_process_of_out_sourced")
	private String detailOfProposedProcessOfOutSourced;

//	impact of change

	@Column(name = "quality_improvement")
	private String qualityImprovement;

	@Column(name = "reduced_lead_time")
	private String reducedLeadTime;

	@Column(name = "cost_reduction")
	private String costReduction;

	@Column(name = "increase_manufacturing_efficiency")
	private String increaseManufacturingEfficiency;

	@Column(name = "others_please_specify")
	private String othersPleaseSpecify;

	@Column(name = "effect_of_changes")
	private String effectOfChanges;

	@Column(name = "risk_assessment")
	private String riskAssessment;

	@Column(name = "proposed_introduction_implementation_date")
	private String proposedIntroductionImplementationDate;

	@Column(name = "supplier_evaluation_report")
	private String supplierEvaluationReport;

	@Column(name = "reliability_functional_report_from_tdc")
	private String reliabilityFunctionalReportFromTDC;

	@Column(name = "customer_approval")
	private String customerApproval;

	@Column(name = "on_job_training_report_from_mfg")
	private String onJobTrainingReportFromMfg;

	@Column(name = "process_audit_report")
	private String processAuditReport;

	@Column(name = "supplier_registration_from")
	private String supplierRegistrationFrom;

	@Column(name = "ppap_isir_required")
	private String ppapIsirRequired;

	@Column(name = "change_request_approval")
	private String changeRequestApproval;

	@ManyToOne
	@JoinColumn(name = "sign_by_purchase")
	private EmployeeMasterVO signByPurchase;

	@Column(name = "purchase_disposition")
	private String purchaseDisposition;

	@ManyToOne
	@JoinColumn(name = "sign_by_tdc")
	private EmployeeMasterVO signByTDC;

	@Column(name = "tdc_disposition")
	private String tdcDisposition;

	@ManyToOne
	@JoinColumn(name = "sign_by_production")
	private EmployeeMasterVO signByProduction;

	@Column(name = "production_disposition")
	private String productionDisposition;

	@ManyToOne
	@JoinColumn(name = "sign_by_quality")
	private EmployeeMasterVO signByQuality;

	@Column(name = "quality_disposition")
	private String qualityDisposition;

	@Column(name = "note")
	private String note;

	@Column(name = "active")
	private boolean active;

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	@Column(name = "screen_name")
	private String screenName = "SUPPLIER CHANGE REQUEST";
	@Column(name = "screen_code")
	private String screenCode = "SCR";

	@JsonGetter("activeStatus")
	public String getActiveStatus() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancelStatus")
	public String getCancelStatus() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
