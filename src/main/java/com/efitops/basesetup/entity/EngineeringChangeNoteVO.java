package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.efitops.basesetup.dto.CreatedUpdatedDate;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "engineering_change_note_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineeringChangeNoteVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "engineering_change_note_basicgen")
	@SequenceGenerator(name = "engineering_change_note_basicgen", sequenceName = "engineering_change_note_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "engineering_change_note_basic_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "from_department")
	private String fromDepartment;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "product_no")
	private String productNo;

	@Column(name = "customer_part_no")
	private String customerPartNo;

//	part details

	@Column(name = "part_no")
	private String partNo;

	@Column(name = "part_description")
	private String partDescription;

//	reason for change

	@Column(name = "customer_requirements")
	private String customerRequirements;

	@Column(name = "value_engineering")
	private String valueEngineering;

	@Column(name = "quality_requirements")
	private String qualityRequirements;

	@Column(name = "purchase_requirements")
	private String purchaseRequirements;

	@Column(name = "in_case_others_please_mention_details")
	private String inCaseOthersPleaseMentionDetails;

//	store and logistics

	@Column(name = "does_change_affect_the_part")
	private String doesChangeChangeThePart;

	@Column(name = "if_yes_action")
	private String ifYesAction;

	@Column(name = "part_to_be_reworked")
	private String partToBeReworked;

	@Column(name = "part_to_be_scrapped")
	private String partToBeScrapped;

//	what is the stock at

	@Column(name = "stores")
	private String stores;

	@Column(name = "wip")
	private String wIP;

	@Column(name = "supplier_include_po")
	private String supplierIncludePo;

	@Column(name = "cost_of_stock_plus_wip")
	private BigDecimal costOfStockPlusWIP;

//	Document changes reuired 1

	@Column(name = "control_plan_reviewed_and_updated")
	private String controlPlanReviewedAndUpdated;

	@Column(name = "any_change_in_work_instruction_sop")
	private String anyChangeInWorkInstructionSOP;

//	stock and design /process validation

	@Column(name = "existing_stock_can_be_used_till_stock_is_exhausted")
	private String existingStockCanBeUsedTillStockIsExhausted;

	@Column(name = "if_no_cost_of_obselecence")
	private String ifNoCostOfObselecence;

	@Column(name = "process_validation_required")
	private String processValidationRequired;

	@Column(name = "validation_detail")
	private String validationDetail;

	@Column(name = "validation_report_to_be_attached")
	private String validationReportToBeAttached;

	@Column(name = "is_there_any_bill_of_material_change_required")
	private String isThereanyBillOfMaterialChangeRequired;

	@Column(name = "if_yes_please_mention")
	private String iFYESPleasemention;

	@Column(name = "expected_date_of_completion")
	private LocalDate ExpectedDateOfCompletion;

//	Conclusion

	@Column(name = "changes_accepted")
	private String changesAccepted;

	@Column(name = "changes_rejected")
	private String changesRejected;

	@Column(name = "changes_involving_cost")
	private BigDecimal changesInvolvingCost;

	@Column(name = "changes_can_be_implemented_by")
	private String changesCanBeImplementedBy;

	@Column(name = "conformation_on_implementation_by_qad")
	private String conformationOnImplementationByQAD;

	@Column(name = "customer_approval_required_or_ not_required")
	private String customerApprovalRequiredOrNotRequired;

//	CFT Approval/concurrence

	@Column(name = "approval_by_tdc_mgr")
	private String approvalByTDCMgr;

	@Column(name = "accepted_by_qad_mgr")
	private String acceptedByQADMgr;

	@Column(name = "non_accepted_qad_reason")
	private String nonAcceptedQADReason;

	@Column(name = "accepted_by_pur_mgr")
	private String acceptedByPURMgr;

	@Column(name = "non_accepted_pur_reason")
	private String nonAcceptedPURReason;

	@Column(name = "accepted_by_prod_mgr")
	private String acceptedbyPRODMgr;

	@Column(name = "non_accepted_prod_reason")
	private String nonAcceptedPRODReason;

	@Column(name = "accepted_by_stores_mgr")
	private String acceptedByStoresMgr;

	@Column(name = "non_accepted_store_reason")
	private String nonAcceptedStoreReason;

	@Column(name = "active")
	private boolean active;

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	@Column(name = "screen_name")
	private String screenName = "ENGINEERING CHANGE NOTE";
	@Column(name = "screen_code")
	private String screenCode = "ECN";

	@OneToMany(mappedBy = "engineeringChangeNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<RemarksVO> remarksVO;

	@OneToMany(mappedBy = "engineeringChangeNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ChangeRequiredVO> changeRequiredVO;

	@OneToMany(mappedBy = "engineeringChangeNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DocumentsChangesVO> documentsChangesVO;

	@OneToMany(mappedBy = "engineeringChangeNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<DocumentsVO> documentsVO;

	@OneToMany(mappedBy = "engineeringChangeNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ProcessChangesVO> processChangesVO;

	@OneToMany(mappedBy = "engineeringChangeNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<InspectionTestingVO> inspectionTestingVO;

	@OneToMany(mappedBy = "engineeringChangeNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PdfAttachmentDrawingVO> pdfAttachmentDrawingVO;

	@OneToMany(mappedBy = "engineeringChangeNoteVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<PdfAttachmentBomVO> pdfAttachmentBomVO;

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
