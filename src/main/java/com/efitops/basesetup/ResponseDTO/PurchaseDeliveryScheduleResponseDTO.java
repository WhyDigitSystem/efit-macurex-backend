package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.service.CustomerResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDeliveryScheduleResponseDTO {

    private Long id;
    private BranchResponseDTO branch;
    private String belongsTo;
    private String docId;
    private LocalDate docDate;
    private LocalDate scheduleStartDate;
    private LocalDate scheduleEndDate;
    private SupplierResponseDTO supplier;
    private EmployeeDetailsDTO preparedBy;
    private String note;
    private String purchaseOrderNo;
    private LocalDate purchaseOrderDate;
    private Long orgId;
    private String financialYear;
    private String active;
    private String cancelRemarks;
    private String createdBy;
    private List<PurchaseDeliveryScheduleDetailsResponseDTO> scheduleDetails;
  
    
}