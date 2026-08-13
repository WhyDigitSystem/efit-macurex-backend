package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.EmployeeResponseDTO;
import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;
import com.efitops.basesetup.service.CustomerResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalPurchaseOrderResponseDTO {

    private Long id;
    private BranchResponseDTO branch;
    private String poNo;
    private String belongsTo;
    private LocalDate poDate;

    private ListOfVlauesDetailsResponseDTO department;

    private CustomerResponseDetailsDTO supplier;
    private GSTStateResponseDTO gstState;
    private String supplierRefNo;
    private String address;
    private Boolean isIgstAppl;
    private LocalDate suppRefDt;
    private String gstnNo;

    private ListOfVlauesDetailsResponseDTO taxCode;
    private Boolean isReverseChrg;

    private String itemType;
    private Boolean indentRequired;
    private ListOfVlauesDetailsResponseDTO dealerType;

    private List<LocalPurchaseOrderDetailsResponseDTO> details;
    private List<LocalPurchaseOrderTaxDetailsResponseDTO> taxDetails;
    private List<LocalPurchaseOrderAttachmentDTO> attachments;

    private String freightType;
    private String packingType;
    private BigDecimal insurance;
    private BigDecimal freight;
    private BigDecimal totalAmount;
    private String modeOfDespatch;
    private String paymentTerms;
    private String deliveryTerms;
    private String amountInWords;
    private String remarks;
    private String notes;
    private EmployeeResponseDTO preparedBy;
    private EmployeeResponseDTO checkedBy;
    private EmployeeResponseDTO authorisedBy;

    private Long orgId;
    private String financialYear;
    private Boolean active;
    private String cancelRemarks;
    private Long createdBy;
    private Long updatedBy;
}