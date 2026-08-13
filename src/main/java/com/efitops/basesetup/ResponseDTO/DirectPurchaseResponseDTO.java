// DirectPurchaseResponseDTO.java
package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;
import com.efitops.basesetup.service.CustomerResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectPurchaseResponseDTO {

    private Long id;
    private BranchResponseDTO branch;
    private String billNo;
    private String belongsTo;
    private LocalDate billDate;
    private ListOfVlauesDetailsResponseDTO department;
    private CustomerResponseDetailsDTO supplier;

    private Long purchaseIndentId;
    private String indentNo;
    private LocalDate indentDate;

    private String gatePassNo;
    private String supplierInvNo;
    private Boolean excisable;
    private LocalDate date;
    private String location;
    private String currency;
    private String taxCode; // taxCode description, snapshotted from TaxDefinitionVO

    private List<DirectPurchaseDetailsResponseDTO> purchaseDetails;
    private List<DirectPurchaseTaxDetailsResponseDTO> taxDetails;

    private BigDecimal totalAmount;
    private String amountInWords;
    private String paymentTerms;
    private String deliveryTerms;
    private String narration;
    private Boolean approved;
    private String notes;
    private BigDecimal freight;

    private Long orgId;
    private String financialYear;
    private Boolean active;
    private String cancelRemarks;
    private Long createdBy;
    private Long updatedBy;
}