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
public class PurchaseShortCloseResponseDTO {

    private Long id;
    private BranchResponseDTO plant;
    private String shortCloseNo;
    private String belongsTo;
    private LocalDate shortCloseDate;
    private String type;
    private CustomerResponseDetailsDTO supplier;

    private Long localPurchaseOrderId;
    private String poNo;
    private LocalDate poDate;

    private String referenceForShortClose;

    private List<PurchaseShortCloseDetailsResponseDTO> details;

    private Long orgId;
    private String financialYear;
    private Boolean active;
    private String cancelRemarks;
    private Long createdBy;
    private Long updatedBy;
}