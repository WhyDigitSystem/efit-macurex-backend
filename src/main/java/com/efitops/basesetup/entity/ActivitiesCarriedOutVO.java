package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.ArrayList;
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
@Table(name = "activities_carried_out_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivitiesCarriedOutVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activities_carried_out_basicgen")
	@SequenceGenerator(name = "activities_carried_out_basicgen", sequenceName = "activities_carried_out_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "activities_carried_out_basic_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;
	
	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;
	
	@ManyToOne
	@JoinColumn(name = "checked_by")
	private EmployeeMasterVO checkedBy;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Column(name = "doc_date")
	private LocalDate docDate = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "select_machine_tool_inst")
	private ToolCategoryVO selectMachineToolInst;
	
	@Column(name = "machine_tool_inst_no")
	private String machineToolInstNo;
	
	@Column(name = "location")
	private String Location;
	
	@ManyToOne
	@JoinColumn(name = "pm_check_list_no")
	private PMCheckListMasterVO pmCheckListNo;
	
	@ManyToOne
	@JoinColumn(name = "maintenance_type")
	private ListOfValuesDetailsVO maintenanceType;
	
	@ManyToOne
	@JoinColumn(name = "from_location")
	private LocationVO fromLocation;
	
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
	private String screenCode = "ACO";

	@Column(name = "screen_name")
	private String screenName = "ACTIVITY CARRIED OUT";
	
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
	
	@OneToMany(mappedBy = "activitiesCarriedOutVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ActivitiesCarriedOutDetailsVO> activitiesCarriedOutDetailsVO = new ArrayList<>();
	
	@OneToMany(mappedBy = "activitiesCarriedOutVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ActivitiesCarriedOutComponentDetailsVO> activitiesCarriedOutComponentDetailsVO = new ArrayList<>();
	
	
}
