package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import com.efitops.basesetup.dto.EmployeeResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceServiceRequestResponseDTO {

    private Long id;

    private ListOfValuesDetailsResponseDTO belongTo;

    private DepartmentResponseDTO department;

    private String mailId;

    private String reportedTime;

    private String phoneNo;

    private String completed;

    private ListOfValuesDetailsResponseDTO priority;

    private LocalDate closingDate;

    private EmployeeResponseDTO requestedBy;

    private EmployeeResponseDTO preparedBy;

    private String approvedBy;

    private String serviceRequired;

    private String remarks;

    private boolean active;

    private Long orgId;

    private String createdBy;

    private String updatedBy;

    private boolean cancel;

    private String cancelRemarks;
    
}