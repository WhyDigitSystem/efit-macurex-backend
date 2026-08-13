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
    private Long localPurchaseOrderId;
    private CustomerResponseDetailsDTO supplier;
    private String poType;
    private Long poId;
    private String poNo;
    private LocalDate poDate;
    private String preparedBy;
    private String note;
    private List<PurchaseDeliveryScheduleDetailsResponseDTO> scheduleDetails;
    private List<PurchaseDeliveryScheduleLineResponseDTO> schedule;
    private Long orgId;
    private String financialYear;
    private Boolean active;
    private String cancelRemarks;
    private Long createdBy;
    private Long updatedBy;
}