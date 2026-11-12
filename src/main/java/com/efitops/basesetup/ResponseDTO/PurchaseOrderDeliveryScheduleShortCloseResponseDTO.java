package com.efitops.basesetup.ResponseDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDeliveryScheduleShortCloseResponseDTO {
    private Long id;
    private String docId;
    private LocalDate docDate;
    private String belongsTo;
    private SupplierResponseDTO supplierCode;
    private String type;
    private String purchaseOrderScheduleNo;
    private String referenceForShortClose;
    private String createdBy;
    private String narration;
    private String active;
    private String cancel;
    private String updatedBy;
    private String cancelRemarks;
    private String screenName;
    private String screenCode;
    private Long orgId;
    private String financialYear;
    private BranchResponseDTO branch;
    private List<PurchaseOrderDeliveryScheduleShortCloseDetailsResponseDTO> purchaseOrderDeliveryScheduleShortCloseDetailsResponseDTO;

}
