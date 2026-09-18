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
public class DeliveryChallanCumGatePassResponseDTO {

    private Long id;

    private String docId;

    private LocalDate docDate;

    private String belongsTo;

    private String type;

    private boolean isIGSTAppl;

    private DepartmentResponseDTO department;

    private String gstnNo;

    private CustomerDropdownResponseDTO customer;
    
    private BranchResponseDTO toBranch;


    private LocationMasterResponseDTO fromLocation;

    private String modeOfTransport;

    private String vehicleNo;

    private WorkOrderResponseDTO workOrderNo;
    private EmployeeMasterResponseDetailsDTO preparedBy;


    private BigDecimal totalQty;

    private String remarks;

    private String createdBy;

    private String active;

    private String cancel;

    private String updatedBy;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;

    private Long orgId;

    private String financialYear;

    private BranchResponseDTO branch;

    private List<DeliveryChallanCumGatePassDetailsResponseDTO>
            deliveryChallanCumGatePassDetails;
}