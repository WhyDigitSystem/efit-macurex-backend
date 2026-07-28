package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "employeemastergen")
	@SequenceGenerator(name = "employeemastergen", sequenceName = "employeemasterseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "employeemasterid")
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

	@Column(name = "temp_city")
	private String tempCity;

	@Column(name = "temp_state")
	private String tempState;

	@Column(name = "temp_country")
	private String tempCountry;

	@Column(name = "temp_pincode")
	private String tempPincode;

	// Permanent Address

	@Column(name = "permanent_address_line")
	private String permanentAddressLine;

	@Column(name = "permanent_address_line2")
	private String permanentAddressLine2;

	@Column(name = "permanent_address_line3")
	private String permanentAddressLine3;

	@Column(name = "permanent_address_line4")
	private String permanentAddressLine4;

	@Column(name = "permanent_city")
	private String permanentCity;

	@Column(name = "permanent_state")
	private String permanentState;

	@Column(name = "permanent_country")
	private String permanentCountry;

	@Column(name = "permanent_pincode")
	private String permanentPincode;

	// Common Fields

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	@Column(name = "active")
	private boolean active;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "cancel_remarks")
	private String cancelRemarks;

	@Column(name = "screen_name")
	private String screenName;

	@Column(name = "screen_code")
	private String screenCode;

	@Column(name = "branch")
	private Long branch;

	@Column(name = "org")
	private Long org;

	@Column(name = "financial_year")
	private String financialYear;
	
//	@Column(name = "employeecode" , unique = true)
//	private String employeeCode;
//	@Column(name = "firstname")
//	private String firstName;
//	@Column(name = "lastname")
//	private String lastName;
//	@Column(name = "employeename")
//	private String employeeName;
//	@Column(name = "fathername")
//	private String fatherName;
//	@Column(name = "gender")
//	private String gender;
//	@Column(name = "bloodgroup")
//	private String bloodGroup;
//	@Column(name = "salutation")
//	private String salutation;
//	@Column(name = "aadhaarno", unique = true)
////	@Pattern(regexp = "\\d{12}", message = "Aadhaar number must be exactly 12 digits")
//	private String aadhaarNo;
//	@Column(name = "dateofbirth")
//	private LocalDate dateOfBirth;
//	@Column(name = "maritalstatus")
//	private String maritalStatus;
//	
//	
//	@Column(name = "createdby")
//	private String createdBy;
//	@Column(name = "modifiedby")
//	private String updatedBy;
//	@Column(name = "cancelremarks")
//	private String cancelRemarks;
//	@Column(name = "cancel")
//	private boolean cancel = false;
//	@Column(name = "active")
//	private boolean active;
//	@Column(name = "orgid")
//	private Long orgId;
//
//	@Column(name = "branch", length = 25)
//	private String branch;
//
//	@Column(name = "branchcode", length = 20)
//	private String branchCode;
//	
//    @Column(name = "finyear", length = 5)
//    private String finYear;
//
//    @Column(name = "screencode", length = 5)
//    private String screenCode = "EM";
//
//    @Column(name = "screenname", length = 25)
//    private String screenName = "EMPLOYEEMASTER";

//	@OneToOne(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private EmployeeDetailsVO employeeDetailsVO;
//
//	@OneToOne(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private EmployeePersonalDetailsVO employeePersonalDetailsVO;
//	
//	@OneToMany(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private List<EmployeeFinanceInformationVO> employeeFinanceInformationVO;
//	
//	@OneToOne(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private EmployeeCommunicationDetailsVO employeeCommunicationDetailsVO;
//	
//	@OneToOne(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private EmployeeComplianceDetailsVO employeeComplianceDetailsVO;
//	
//	@OneToMany(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private List<EmployeeLoanDetailsVO> employeeLoanDetailsVO;

//    @OneToOne(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private EmployeeDetailsVO employeeDetailsVO;
//
//    @OneToOne(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private EmployeePersonalDetailsVO employeePersonalDetailsVO;
//
//    @OneToOne(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private EmployeeCommunicationDetailsVO employeeCommunicationDetailsVO;
//
//    @OneToOne(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private EmployeeComplianceDetailsVO employeeComplianceDetailsVO;
//
//    @OneToMany(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<EmployeeFinanceInformationVO> employeeFinanceInformationVO;
//
//    @OneToMany(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<EmployeeLoanDetailsVO> employeeLoanDetailsVO;
//    
//    @OneToMany(mappedBy = "employeeMasterVO",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
// @JsonManagedReference
// private List<EmployeeAttachmentVO> documents;

	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
}


