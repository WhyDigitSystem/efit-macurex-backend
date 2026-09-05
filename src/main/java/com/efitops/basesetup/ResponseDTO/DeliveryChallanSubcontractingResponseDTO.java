package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryChallanSubcontractingResponseDTO {

    private Long id;

    private String docId;

    private LocalDate docDate;

    private BranchResponseDTO branch;

    private DepartmentResponseDTO department;

    private String belongsTo;

    private CustomerDropdownResponseDTO vendor;

    private LocationMasterResponseDTO partyLocation;

    private String jobOrderNo;

    private ItemResponseDTO incomingItem;

    private TransportResponseDTO transportName;

    private String vehicleNo;

    private BomResponseDTO sfgBomId;

    private BigDecimal qty;

    private String timeOfIssue;

    private String dcType;

    private EmployeeResponseDTO preparedBy;

    private EmployeeResponseDTO approvedBy;

    private String approvalByStores;

    private String remarks;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private String active;

    private String cancel;

    private String cancelRemarks;

    private String screenCode;

    private String screenName;

    private List<DeliveryChallanSubcontractingDetailsResponseDTO> details;
}