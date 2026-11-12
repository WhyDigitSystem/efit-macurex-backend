package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDeliveryScheduleDTO {

    private Long id;
    private Long branch;
    private String belongsTo;
//    private LocalDate docDate;
    private LocalDate scheduleStartDate;
    private LocalDate scheduleEndDate;
    private Long supplier;
    private Long preparedBy;
    private String note;

    private String purchaseOrderNo;   // "PURCHASE_CONTRACT" or "LOCAL_PURCHASE_ORDER"
    private LocalDate purchaseOrderDate;
  
    private Long orgId;
    private String financialYear;
    private boolean active;
    private String cancelRemarks;
    private String createdBy;
    private List<PurchaseDeliveryScheduleDetailsDTO> scheduleDetails;
    
}