package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "engineering_change_record_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineeringChangeRecordVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "engineering_change_record_basicgen")
	@SequenceGenerator(name = "engineering_change_record_basicgen", sequenceName = "engineering_change_record_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "engineering_change_record_basic_id")
	private Long id;

	@Column(name = "doc_id")
	private String docId;

	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@Column(name = "from_department")
	private String fromDepartment;

	@Column(name = "customer_name")
	private String customerName;

	@ManyToOne
	@JoinColumn(name = "requested_by")
	private EmployeeMasterVO requestedBy;

	@Column(name = "reason_for_change")
	private String reasonForChange;

	@Column(name = "product_description")
	private String productDescription;

	@Column(name = "engineering_drawing_change")
	private String engineeringDrawingChange;

	@Column(name = "bom_change")
	private String bomChange;

//	REMARKS
	@Column(name = "accepted")
	private String accepted;

	@Column(name = "rejected")
	private String rejected;

	@ManyToOne
	@JoinColumn(name = "approved_by")
	private EmployeeMasterVO approvedBy;

	@Column(name = "approved")
	private String approved;

//	product no details
	@Column(name = "customer_product_no")
	private String customerProductNo;

	@Column(name = "company_product_no")
	private String companyProductNo;

//	partno
	@Column(name = "part_no")
	private String partNo;

	@Column(name = "part_description")
	private String partDescription;

//	for tdc department
	@Column(name = "customer_approval")
	private String customerApproval;

	@Column(name = "drawing_which_required_change")
	private String drawingWhichRequiredChange;

	@Column(name = "document_which_required_change")
	private String documentWhichRequiredChange;

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
	private String screenName = "ENGINEERING CHANGE RECORD";
	@Column(name = "screen_code")
	private String screenCode = "ECR";

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

	@OneToMany(mappedBy = "engineeringChangeRecordVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<EngineeringChangeRecordAttachmentVO> engineeringChangeRecordAttachmentVO = new ArrayList<>();
}
