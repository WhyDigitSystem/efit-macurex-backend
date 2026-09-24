package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineSettingPlanDTO {

    private Long id;

    private Long branch;

    private Long item;

    private String operationNo;

    private String operationName;

    private String processSheetNo;

    private String machineNo;

    private String machineName;

    private String make;

    private String toolReplacementPlan;

    private Long preparedBy;

    private Long approvedBy;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private Long orgId;

    private String financialYear;

    private List<MachineSettingPlanDetailsDTO> details;
}