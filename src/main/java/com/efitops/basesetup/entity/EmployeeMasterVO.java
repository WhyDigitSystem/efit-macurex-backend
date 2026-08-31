package com.efitops.basesetup.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
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
@Table(name = "employeemaster")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeMasterVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeemastergen")
	@SequenceGenerator(name = "employeemastergen", sequenceName = "employeemasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "employeemaster_id")
	private Long id;

	@Column(name = "employee_id")
	private String employeeId;

	@Column(name = "sur_name")
	private String surName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "father_husband_name")
	private String fatherHusbandName;

	@Column(name = "title")
	private String title;

	@Column(name = "account_head")
	private String accountHead;

	@Column(name = "sex")
	private String sex;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "telephone")
	private Long telephone;

	@Column(name = "mobile")
	private Long mobile;

	@Column(name = "email")
	private String email;

	@Column(name = "qualification")
	private String qualification;

	@Column(name = "grade")
	private String grade;

	@Column(name = "passport_no")
	private String passportNo;

	@Column(name = "pan_no")
	private String panNo;

	@Column(name = "blood_group")
	private String bloodGroup;

	@Column(name = "nominee")
	private String nominee;

	// Temporary Address

	@Column(name = "temp_address_line")
	private String tempAddressLine;

	@ManyToOne
	@JoinColumn(name = "temp_city")
	private CityVO tempCity;

	@ManyToOne
	@JoinColumn(name = "temp_state")
	private StateVO tempState;

	@ManyToOne()
	@JoinColumn(name = "temp_country")
	private CountryVO tempCountry;

	@Column(name = "temp_pincode")
	private Long tempPincode;

	// Permanent Address

	@Column(name = "permanent_address_line")
	private String permanentAddressLine;

	@ManyToOne
	@JoinColumn(name = "permanent_city")
	private CityVO permanentCity;

	@ManyToOne
	@JoinColumn(name = "permanent_state")
	private StateVO permanentState;

	@ManyToOne
	@JoinColumn(name = "permanent_country")
	private CountryVO permanentCountry;

	@Column(name = "permanent_pincode")
	private Long permanentPincode;

	// OtherInformation

	@Column(name = "card_no")
	private String cardNo;

	@Column(name = "temporary_card_no")
	private String temporaryCardNo;

	@Column(name = "date_of_joining")
	private LocalDate dateOfJoining;

	@ManyToOne
	@JoinColumn(name = "department")
	private DepartmentVO department;

	@ManyToOne
	@JoinColumn(name = "designation")
	private DesignationVO designation;

	@Column(name = "nature_of_employment")
	private String natureOfEmployment;

	@Column(name = "over_time_applicable")
	private String overTimeApplicable;

	@Column(name = "reference_by")
	private String referenceBy;

	@ManyToOne
	@JoinColumn(name = "okd_by")
	private EmployeeMasterVO okdBy;

	@Column(name = "mode_of_payment")
	private String modeOfPayment;

	@Column(name = "bank_account_no")
	private String bankAccountNo;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "pf_no")
	private String pfNo;

	@Column(name = "esi_no")
	private String esiNo;

	@Column(name = "esi_disp_name")
	private String esiDispName;

	@Column(name = "vpf_percentage")
	private BigDecimal vpfPercentage;

	@Column(name = "date_of_confirmation")
	private LocalDate dateOfConfirmation;

	@Column(name = "training_start_date")
	private LocalDate trainingStartDate;

	@Column(name = "date_of_resignation")
	private LocalDate dateOfResignation;
	
	@Column(name = "training_end_date")
	private LocalDate trainingEndDate;

	@Column(name = "notice_period")
	private Integer noticePeriod;

	@Column(name = "current_salary_period_start")
	private LocalDate currentSalaryPeriodStart;

	@Column(name = "current_salary_period_end")
	private LocalDate currentSalaryPeriodEnd;

	@Column(name = "emp_name")
	private String employeeName;

	// Common Fields

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
	private String screenName ="EMPLOYEE";

	@Column(name = "screen_code")
	private String screenCode= "MAC";

	@Column(name = "org_id")
	private Long orgId;

//	@Column(name = "financial_year")
//	private String financialYear;

	@ManyToOne
	@JoinColumn(name = "branch")
	private BranchVO branch;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
