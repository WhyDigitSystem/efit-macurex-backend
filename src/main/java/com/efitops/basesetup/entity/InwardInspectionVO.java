package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.time.LocalTime;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inward_inspection_basic")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InwardInspectionVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inward_inspection_basicgen")
	@SequenceGenerator(name = "inward_inspection_basicgen", sequenceName = "inward_inspection_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "inward_inspection_basic_id", columnDefinition = "BIGINT DEFAULT 0")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@Column(name = "inward_type")
	private String inwardType;

	@Column(name = "mrin_grn_no")
	private String mrinGrnNo;

	@Column(name = "mrin_grn_date")
	private LocalDate mrinGrnDate;

	@ManyToOne
	@JoinColumn(name = "supplier_code")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "gstState", "branch", "city", "country", "state", "customerContactDetails", "customerShippingDetails", "CustomerItemDetailsVO"})
	private CustomerVO supplierCode;

	@Column(name = "time_of_inspection")
	private LocalTime timeOfInspection = LocalTime.now();

	@Column(name = "grn_time")
	private LocalTime grnTime = LocalTime.now();

	@Column(name = "iso_expiary_date")
	private LocalDate isoExpiaryDate;

	@Column(name = "po_pc_jo_no")
	private String poPcJoNo;

	@Column(name = "ppap_sample")
	private String ppapSample;

	@Column(name = "schedule_no")
	private String scheduleNo;

	@Column(name = "sup_inv_no")
	private String supInvNo;

	@Column(name = "sup_inv_dt")
	private LocalDate supInvDt;

	@Column(name = "considerations")
	private String considerations;

	@Column(name = "disposal_action")
	private String disposalAction;

	@ManyToOne
	@JoinColumn(name = "checked_by")
	private EmployeeMasterVO checkedBy;

	@ManyToOne
	@JoinColumn(name = "approved_by")
	private EmployeeMasterVO approvedBy;

	@Column(name = "result")
	private String result;

	@Column(name = "notes")
	private String notes;

	// commonfileds

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "modified_by")
	private String updatedBy;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_name")
	private String screenName = "INWARD";

	@Column(name = "screen_code")
	private String screenCode = "OA";

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	// purchaseLocal

	@OneToMany(mappedBy = "inwardInspectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<InwardInspectionDetailsVO> inwardInspectionDetailsVO;

	@OneToMany(mappedBy = "inwardInspectionVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<InwardInspectionFileUploadDetailsVO> inwardInspectionFileUploadDetailsVO;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
