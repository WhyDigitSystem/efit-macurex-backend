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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
	@Column(name = "employeecode" , unique = true)
	private String employeeCode;
	@Column(name = "firstname")
	private String firstName;
	@Column(name = "lastname")
	private String lastName;
	@Column(name = "employeename")
	private String employeeName;
	@Column(name = "fathername")
	private String fatherName;
	@Column(name = "gender")
	private String gender;
	@Column(name = "bloodgroup")
	private String bloodGroup;
	@Column(name = "salutation")
	private String salutation;
	@Column(name = "aadhaarno", unique = true)
//	@Pattern(regexp = "\\d{12}", message = "Aadhaar number must be exactly 12 digits")
	private String aadhaarNo;
	@Column(name = "dateofbirth")
	private LocalDate dateOfBirth;
	@Column(name = "maritalstatus")
	private String maritalStatus;
	
	
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "active")
	private boolean active;
	@Column(name = "orgid")
	private Long orgId;

	@Column(name = "branch", length = 25)
	private String branch;

	@Column(name = "branchcode", length = 20)
	private String branchCode;
	
    @Column(name = "finyear", length = 5)
    private String finYear;

    @Column(name = "screencode", length = 5)
    private String screenCode = "EM";

    @Column(name = "screenname", length = 25)
    private String screenName = "EMPLOYEEMASTER";

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

    @OneToOne(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
    @JsonManagedReference
    private EmployeeDetailsVO employeeDetailsVO;

    @OneToOne(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
    @JsonManagedReference
    private EmployeePersonalDetailsVO employeePersonalDetailsVO;

    @OneToOne(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
    @JsonManagedReference
    private EmployeeCommunicationDetailsVO employeeCommunicationDetailsVO;

    @OneToOne(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
    @JsonManagedReference
    private EmployeeComplianceDetailsVO employeeComplianceDetailsVO;

    @OneToMany(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<EmployeeFinanceInformationVO> employeeFinanceInformationVO;

    @OneToMany(mappedBy = "employeeMasterVO", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<EmployeeLoanDetailsVO> employeeLoanDetailsVO;
    
    @OneToMany(mappedBy = "employeeMasterVO",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
 @JsonManagedReference
 private List<EmployeeAttachmentVO> documents;

	
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


