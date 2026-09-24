package com.efitops.basesetup.entity;

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
@Table(name = "authorization_for_breakdown_basic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationForBreakdownVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorization_for_breakdown_basicgen")
	@SequenceGenerator(name = "authorization_for_breakdown_basicgen", sequenceName = "authorization_for_breakdown_basicseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "authorization_for_breakdown_basic_id")
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
	
	@Column(name = "rectification_no")
	private String rectificationNo;
	
	@Column(name = "rectification_date")
	private String rectificationDate;
	
	@Column(name = "breakdown_no")
	private String breakdownNo;
	
	@Column(name = "breakdown_date")
	private String breakdownDate;
	
	@Column(name = "working")
	private String working;
	
	@Column(name = "problem")
	private String problem;
	
	@Column(name = "solution")
	private String solution;
	
	@Column(name = "machine_no")
	private String machineNo;
	
	@Column(name = "rectified_time")
	private String rectifiedTime;
	
	@ManyToOne
	@JoinColumn(name = "authorized_by")
	private EmployeeMasterVO authorizedBy;
	
	@Column(name = "reason_if_no")
	private String ReasonIfNo;
	
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
	private String screenCode = "AUFBR";

	@Column(name = "screen_name")
	private String screenName = "AUTHORIZATION FOR BREAKDOWN";
	
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
