package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RejectionInvoiceResponseDTO {

    private Long id;

    private String docId;

    private LocalDate docDate;

    private String monthYear;

    private String belongsTo;

    private String docType;

    private CustomerOtherSalesResponseDTO customer;

    private DespatchInstructionResponseDocIdDTO diNo;

    private String stockPosting;

    private String excisable;

    private LocationMasterResponseDTO location;

    private String vehicle;

    private LocalTime timeOfIssue;

    private LocalDate timeOfIssueDate;

    private CurrencyResponseDTO currency;

    private LocalTime timeOfRemoval;

    private LocalDate timeOfRemovalDate;

    private String kanbanCardNo;

    private String invoiceType;

    private String schNo;

    private LocalDate schDate;

    private BigDecimal exchangeRate;

    private BigDecimal totalInsurance;

    private BigDecimal totalFreight;

    private BigDecimal totalAssVal;

    private String modeOfTransport;

    private BigDecimal netAmount;

    private String amountInWords;

    private String deliveryTo;

    private String paymentTerms;

    private String purchaseOrder;

    private String purchaseOrderDate;

    private String createdBy;

    private String isIgstApplicable;

    private String active;

    private String cancel;

    private String updatedBy;

    private String cancelRemarks;

    private String screenName;

    private String screenCode;

    private Long orgId;

    private String financialYear;

    private BranchResponseDTO branch;

    // Rejection Invoice specific fields
    private String rejectionType;

    private String reasonForRejection;

    private String originalInvoiceNo;

    private LocalDate originalInvoiceDate;

    // Details and Tax lists
    private List<RejectionInvoiceDetailsResponseDTO> rejectionInvoiceDetailsResponseDTO;

    private List<RejectionInvoiceTaxDetailsResponseDTO> rejectionInvoiceTaxDetailsResponseDTO;
}