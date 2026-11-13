package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "machine_tool_breakdown_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineToolBreakdownVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine_tool_breakdown_basicgen")
	@SequenceGenerator(name = "machine_tool_breakdown_basicgen", sequenceName = "machine_tool_breakdown_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "machine_tool_breakdown_basic_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate .now();
	
	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;
	
	@ManyToOne
	@JoinColumn(name = "select_machine_tool_inst")
	private ToolCategoryVO selectMachineToolInst;
	
	@Column(name = "machine_tool_id_inst")
	private String machineToolIdInst;
	
	@Column(name = "machine_name")
	private String machineName;
	
	@Column(name = "location")
	private String location;
	
	@ManyToOne
	@JoinColumn(name = "pm_check_list_no")
	private PMCheckListMasterVO pmCheckListNo;
	
	@Column(name = "breakdown_time")
	private LocalTime breakdownTime;
	
	@Column(name = "reported_time")
	private LocalTime reportedTime;
	
	@Column(name = "reported_date")
	private LocalDate reportedDate;
	
	@Column(name = "machine_tool_breakdown_image")
	private String image;
	
	@ManyToOne
	@JoinColumn(name = "operator_name")
	private EmployeeMasterVO operatorName;
	
	@ManyToOne
	@JoinColumn(name = "maintenance_type")
	private ListOfValuesDetailsVO maintenanceType;
	
	@ManyToOne
	@JoinColumn(name = "nature_of_breakdown")
	private ListOfValuesDetailsVO natureOfBreakdown;
	
	@Column(name = "nature_of_problem")
	private String natureOfProblem;
	
	@Column(name = "estimated_time")
	private String estimatedTime;
	
	@ManyToOne
	@JoinColumn(name = "breakdown_type")
	private ListOfValuesDetailsVO breakdownType;
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "active")
	private boolean active;

	@Column(name = "org_id")
	private Long orgId;

	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "financial_year")
	private String FinancialYear;
	
	
	@Column(name = "modified_by")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancel_remarks")
	private String cancelRemarks;
	@Column(name = "screen_name")
	private String screenName = "MACHINE TOOL BREAKDOWN";
	@Column(name = "screen_code")
	private String screenCode = "MTB";

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
	
	@OneToMany(mappedBy = "machineToolBreakdownVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<MachineToolBreakdownAttachmentVO> MachineToolBreakdownAttachmentVO = new ArrayList<>();
	
}
