package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.ResponseDTO.CityResponseDTO;
import com.efitops.basesetup.ResponseDTO.CountryResponseDTO;
import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.entity.CityVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeMasterResponseDTO {

	private Long id;

	private String employeeId;

	private String employeeName;

	private String surName;

	private String middleName;

	private String fatherHusbandName;

	private String title;

	private String accountHead;

	private String sex;

	private LocalDate dateOfBirth;

	private Long telephone;

	private Long mobile;

	private String email;

	private String qualification;

	private String grade;

	private String passportNo;

	private String panNo;

	private String bloodGroup;

	private String nominee;

	// Temporary Address

	private String tempAddressLine;

	private CityResponseDTO tempCitys;

	private StateResponseDTO tempState;

	private CountryResponseDTO tempCountry;

	private Long tempPincode;

	// Permanent Address

	private String permanentAddressLine;

	private CityResponseDTO permanentCitys;

	private StateResponseDTO permanentState;

	private CountryResponseDTO permanentCountry;

	private Long permanentPincode;

	// Other Information

	private String cardNo;

	private String temporaryCardNo;

	private LocalDate dateOfJoining;

	private BranchResponseDTO plant;

	private DepartmentResponseDTO department;

	private DesignationResponseDTO designation;

	private String natureOfEmployment;

	private String overTimeApplicable;

	private String referenceBy;

	private EmployeeResponseDTO okdBy;

	private String modeOfPayment;

	private String bankAccountNo;

	private String bankName;

	private String pfNo;

	private String esiNo;

	private String esiDispName;

	private BigDecimal vpfPercentage;

	private LocalDate dateOfConfirmation;

	private String informationActive;

	private LocalDate trainingStartDate;

	private LocalDate trainingEndDate;

	private Integer noticePeriod;

	private LocalDate currentSalaryPeriodStart;

	private LocalDate currentSalaryPeriodEnd;

	// Common

	private String createdBy;

	private String updatedBy;

	private boolean active;

	private boolean cancel;

	private String cancelRemarks;

	private String screenName;

	private String screenCode;

	private Long orgId;

	private String financialYear;

	private BranchResponseDTO branch;
}