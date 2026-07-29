package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeMasterDTO {

	private Long id;

	@NotBlank(message = "Surname is required")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid surname")
	private String surName;

	private String middleName;

	private String fatherHusbandName;

	private String title;

	private String accountHead;

	private String sex;

	private LocalDate dateOfBirth;

	private Long telephone;

	@NotNull(message = "Mobile number is required")
	@Digits(integer = 10, fraction = 0, message = "Mobile number must be 10 digits")
	private Long mobile;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email address")
	private String email;

	private String qualification;

	private String grade;

	private String passportNo;

	private String panNo;

	private String bloodGroup;

	private String nominee;

	// Temporary Address

	private String tempAddressLine;

	private String tempCity;

	private Long tempStateId;

	private Long tempCountryId;

	private Long tempPincode;

	// Permanent Address

	private String permanentAddressLine;

	private String permanentCity;

	private Long permanentStateId;

	private Long permanentCountryId;

	private Long permanentPincode;

	// Other Information

	private String cardNo;

	private String temporaryCardNo;

	private LocalDate dateOfJoining;

	private Long plantId;

	private Long departmentId;

	private Long designationId;

	private String natureOfEmployment;

	private String overTimeApplicable;

	private String referenceBy;

	private Long okdById;

	private String modeOfPayment;

	private String bankAccountNo;

	private String bankName;

	private String pfNo;

	private String esiNo;

	private String esiDispName;

	private BigDecimal vpfPercentage;

	private LocalDate dateOfConfirmation;

	private String information_active;

	private LocalDate trainingStartDate;

	private LocalDate trainingEndDate;

	private Integer noticePeriod;

	private LocalDate currentSalaryPeriodStart;

	private LocalDate currentSalaryPeriodEnd;

	// Common Fields

	private String createdBy;

	private String updatedBy;

	private String cancelRemarks;

	private String screenName;

	private String screenCode;

	private Long orgId;

	private String financialYear;

	private Long branchId;
}