package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "machine_tool_rectification_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineToolRectificationVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine_tool_rectification_basicgen")
	@SequenceGenerator(name = "machine_tool_rectification_basicgen", sequenceName = "machine_tool_rectification_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "machine_tool_rectification_basic_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;
	
	@Column(name = "breakdown_no")
	private String  breakdownNo;
	
	@Column(name  = "breakdown_date")
	private LocalDate breakdownDate;
	
	@ManyToOne
	@JoinColumn(name = "attend_by")
	private EmployeeMasterVO attendBy;
	
	@Column(name = "time")
	private String time;
	
	@Column(name = "machine_tool_no")
	private String machineToolNo;
	
	@Column(name = "rectification_time")
	private LocalDateTime rectificationTime;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "cause")
	private String cause;
	
	@Column(name = "maintenance_type")
	private String maintenanceType;
	
	@Column(name = "Action_taken")
	private String actionTaken;
	
	@Column(name = "nature_of_problem")
	private String natureOfProblem;
	
	@ManyToOne
	@JoinColumn(name= "carried_out_by")
	private EmployeeMasterVO carriedOutBy;
	
	@Column(name = "time_taken_for_rectification")
	private String timeTakenForRectification;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "spares_used")
	private String sparesUsed;
	
	@ManyToOne
	@JoinColumn(name = "prepared_by")
	private EmployeeMasterVO preparedBy;
	
	@ManyToOne
	@JoinColumn(name = "approved_by")
	private EmployeeMasterVO approvedBy;
	
	@Column(name = "remarks")
	private String remarks;
	
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
	private String screenCode = "MTR";

	@Column(name = "screen_name")
	private String screenName = "MACHINE TOOL RECTIFICATION";
	
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
