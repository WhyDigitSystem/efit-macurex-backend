package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineSettingPlanResponseDTO {

    private Long id;

    private String docId;

    private LocalDate docDate;

    private BranchResponseDTO branch;

    private ItemResponse1DTO item;

    private String operationNo;

    private String operationName;

    private String processSheetNo;

    private String machineNo;

    private String machineName;

    private String make;

    private String toolReplacementPlan;

    private EmployeeResponseDTO preparedBy;

    private EmployeeResponseDTO approvedBy;

    private String createdBy;

    private boolean active;

    private boolean cancel;

    private String updatedBy;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;

    private Long orgId;

    private String financialYear;

    private List<MachineSettingPlanDetailsResponseDTO> details;
}