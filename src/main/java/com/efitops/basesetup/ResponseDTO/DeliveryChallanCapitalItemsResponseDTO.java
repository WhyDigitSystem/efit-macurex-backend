package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeMasterResponseDTO;
import com.efitops.basesetup.dto.LocationResponseDTO;
import com.efitops.basesetup.service.CustomerResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChallanCapitalItemsResponseDTO {

    private Long id;

    private BranchResponseDTO branch;

    private String docId;

    private LocalDate docDate;

    private String belongsTo;

    private DepartmentResponseDTO department;

    private CustomerResponseDetailsDTO vendor;

    private String indentNo;

    private LocationMasterResponseDTO customerLocation;

    private String transportName;

    private String vehicleNo;

    private String dcType;

    private String approvalByStores;

    private EmployeeDropdownResponseDTO preparedBy;

    private EmployeeDropdownResponseDTO approvedBy;

    private String remarks;

    private String createdBy;

    private boolean active;

    private boolean cancel;

    private String updatedBy;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;

    private Long orgId;

    private String financialYear;

    private List<DeliveryChallanCapitalItemsDetailsResponseDTO> details;
}