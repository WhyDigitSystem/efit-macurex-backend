package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InprocessInspectionResponseDTO {

    private Long id;
    private String docId;
    private LocalDate docDate;

    private BranchResponseDTO branch;
    private DepartmentResponseDTO department;
    private ControlPlanResponseDetailsDTO controlPlan;
    private CustomerDropdownResponseDTO customer;
    private OperationDropdownResponseDTO operationNo;

    private String specification;

    private ItemResponse1DTO partNo;

    private LocationMasterResponseDTO fromLocation;

    private BigDecimal stock;

    private OperationMachineDropdownResponseDTO machineNo;

    private ShiftResponseDTO shift;

    private BigDecimal producedQty;
    private BigDecimal acceptedQty;
    private BigDecimal rejectedQty;
    private BigDecimal rewQty;

    private EmployeeDropdownResponseDTO inspectedBy;
    private EmployeeDropdownResponseDTO verifiedBy;

    private LocationMasterResponseDTO toLocation;

    private String ncDetail;
    private String actionTaken;

    private String createdBy;
    private boolean active;
    private boolean cancel;
    
    private String updatedBy;
    private String cancelRemarks;

    private String screenName;
    private String screenCode;

    private Long orgId;
    private String financialYear;

    private List<InprocessInspectionDetailsResponseDTO> details;
}