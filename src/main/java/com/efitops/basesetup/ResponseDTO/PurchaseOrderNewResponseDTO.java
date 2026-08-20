package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderNewResponseDTO {
    private Long id;
    private String docId;
    private LocalDate docDate;
    private LocalDate orderPlacedDate;
    private String poType;
    private String belongsTo;
    private String isIgstApplicable;
    private String isReverseCharge;
    private String itemType;
    private String indentRequired;
    private String active;
    private String cancelRemarks;
    private Long orgId;
    private String financialYear;
    private String termsAndConditions;
    private String remarks;
    private String freightType;
    private String packingType;
    private String insurance;
    private String freight;
    private String modeOfDespatch;
    private String paymentTerms;
    private String deliveryTerms;
    private String notes;
    private String preparedBy;
    private String checkedBy;
    private String authorisedBy;
    private BigDecimal totalAmount;
    private String amountInWord;

    private BranchResponseDTO branch;
    private DepartmentResponseDTO department;
    private SupplierResponseDTO supplierCode;

    private List<PurchaseOrderLocalDetailsResponseDTO> purchaseOrderLocalDetailsResponseDTO;
    private List<PurchaseOrderLocalTaxDetailsResponseDTO> purchaseOrderLocalTaxDetailsResponseDTO;
    private List<PurchaseOrderLocalFileUploadDetailsResponseDTO> purchaseOrderLocalFileUploadDetailsResponseDTO;

}
