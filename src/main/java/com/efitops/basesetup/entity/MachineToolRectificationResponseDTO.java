package com.efitops.basesetup.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.efitops.basesetup.ResponseDTO.DepartmentResponseDTO;
import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeMasterDetailsReponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineToolRectificationResponseDTO {
	
    private Long id;

    private BranchResponseDTO branch;
    
   

    private DepartmentResponseDTO department;

    private String breakdownNo;

    private LocalDate breakdownDate;

    private EmployeeMasterDetailsReponseDTO attendBy;

    private String time;

    private String machineToolNo;

    private LocalDateTime rectificationTime;

    private String description;

    private String cause;

    private String maintenanceType;

    private String actionTaken;

    private String natureOfProblem;

    private EmployeeMasterDetailsReponseDTO carriedOutBy;

    private String timeTakenForRectification;

    private String location;

    private String sparesUsed;

    private EmployeeMasterDetailsReponseDTO preparedBy;

    private EmployeeMasterDetailsReponseDTO approvedBy;

    private String remarks;

    private Long orgId;

    private String financialYear;

    private String active;

    private String cancelRemarks;

    private String createdBy;

}
