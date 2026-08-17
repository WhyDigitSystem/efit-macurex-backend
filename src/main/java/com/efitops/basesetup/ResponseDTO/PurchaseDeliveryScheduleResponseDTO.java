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
    private String docNo;
    private LocalDate docDate;
    private LocalDate schStartDate;
    private LocalDate schEndDate;
    private SupplierResponseDTO supplier;
    private String purchaseOrderNo;
    private LocalDate purchaseOrderDate;
    private Long orgId;
    private String financialYear;
    private String active;
    private String cancelRemarks;
    private String createdBy;
    private List<PurchaseDeliveryScheduleDetailsResponseDTO> scheduleDetails;
  
    
}