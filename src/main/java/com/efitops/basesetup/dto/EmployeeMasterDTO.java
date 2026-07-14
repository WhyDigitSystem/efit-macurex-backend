package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeMasterDTO {

    private Long id;

    private String employeeCode;

    @NotBlank(message = "First name required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid first name")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid last name")
    private String lastName;

    @NotBlank(message = "Employee name required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid employee name")
    private String employeeName;

    @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid father name")
    private String fatherName;

    private String gender;
    private String bloodGroup;
    private String salutation;

    @Pattern(regexp = "\\d{12}", message = "Invalid Aadhaar")
    private String aadhaarNo;

    private LocalDate dateOfBirth;
    private String maritalStatus;

    private String createdBy;
    private boolean active;
    private Long orgId;

    private String branch;
    private String branchCode;
    private String finYear;

    @Valid
    private EmployeeDetailsDTO employeeDetailsDTO;

    @Valid
    private EmployeePersonalDetailsDTO employeePersonalDetailsDTO;

    @Valid
    private List<EmployeeFinanceInformationDTO> employeeFinanceInformationDTO;

    @Valid
    private EmployeeCommunicationDetailsDTO employeeCommunicationDetailsDTO;

    @Valid
    private EmployeeComplianceDetailsDTO employeeComplianceDetailsDTO;

    @Valid
    private List<EmployeeLoanDetailsDTO> employeeLoanDetailsDTO;
}