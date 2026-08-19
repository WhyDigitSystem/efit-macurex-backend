package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
// NOTE: GSTStateResponseDTO is in this SAME package (com.efitops.basesetup.ResponseDTO) in your codebase, so no import needed.
// CustomerResponseDetailsDTO / ListOfVlauesDetailsResponseDTO / PurchaseContractAttachmentDTO live in com.efitops.basesetup.dto.
import com.efitops.basesetup.service.CustomerResponseDetailsDTO;
import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;
import com.efitops.basesetup.dto.PurchaseContractAttachmentDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseContractResponseDTO {

    private Long id;

    private BranchResponseDTO branch;

//    private String docId;
//
//    private LocalDate docDate;

    private DepartmentResponseDTO department;

    private CustomerResponseDetailsDTO supplier;

    private GSTStateResponseDTO gstState;

    private LocalDate validFrom;

    private LocalDate validTo;

    private String isIgstAppl;

    private String purchaseOrderType;

    // charges summary
    private String modeOfDespatch;
    private String paymentTerms;
    private String delivery;
    private String freightType;
    private String packingType;
    private BigDecimal insuranceAmount;
    private String bank;
    private String accounts;
    private String swiftCode;
    private String checkedBy;
    private String preparedBy;
    private String authorisedBy;
    private String freightForwarder;
    private String notes;
    private String termsConditions;
    private Long orgId;
    private String financialYear;
    private String active;
    private String cancelRemarks;
    private String createdBy;

    private List<PurchaseContractDetailsResponseDTO> details;
    private List<PurchaseContractTaxDetailsResponseDTO> taxDetails;
    private List<PurchaseContractAttachmentDTO> attachments;

    
    
}