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
@Table(name = "set_up_approval_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUpApprovalVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "set_up_approval_basicgen")
	@SequenceGenerator(name = "set_up_approval_basicgen", sequenceName = "set_up_approval_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "set_up_approval_basic_id")
    private Long id;
	
	@ManyToOne
	@JoinColumn(name  = "branch")
	private BranchVO branch;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "shift")
	private ShiftVO shift;
	
	@ManyToOne
	@JoinColumn(name = "item")
	private ItemMasterVO item;
	
	@Column(name = "process_sheet_no")
	private String processSheetNo;
	
	@ManyToOne
	@JoinColumn(name = "customer")
	private CustomerVO customer;
	
	@Column(name = "control_plan")
	private String controlPlan;
	
	@ManyToOne
	@JoinColumn(name = "checked_by")
	private EmployeeMasterVO checkedBy;
	
	@ManyToOne
	@JoinColumn(name = "approved_by")
	private EmployeeMasterVO approvedBy;
	
	@Column(name = "recommendedForProduction")
	private String recommendedForProduction;
	
	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "financial_year")
	private String financialYear;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel = false;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modified_by")
	private String updatedBy;

	@Column(name = "screen_code")
	private String screenCode = "SUA";

	@Column(name = "screen_name")
	private String screenName = "SET UP APPROVAL";

   
    
	@OneToMany(mappedBy = "setUpApprovalVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SetUpApprovalDetailsVO> setUpApprovalDetailsVO = new ArrayList<>();
	
	@OneToMany(mappedBy = "setUpApprovalVO", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SetUpApprovalParametersDetailsVO> setUpApprovalParametersDetailsVO = new ArrayList<>();
	
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
