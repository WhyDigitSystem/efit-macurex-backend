package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.SalesReturnTaxDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReturnResponseDTO {

    private Long id;

    private String docId;

    private LocalDate docDate;

    private BranchResponseDTO branch;

    private String belongsTo;

    private String invoiceNo;

    private LocalDate invoiceDate;

    private String customerInvoiceNo;

    private LocalDate customerInvoiceDate;

    private String gatePassNo;

    private CustomerDropdownResponseDTO customer;

    private LocationMasterResponseDTO location;

    private LocalDate date;

    private ListOfValuesDetailsResponseDTO returnType;

    private String approvedByAccounts;

    private String currency;

    private BigDecimal exchangeRate;

    private String invoiceReferenceType;

    private boolean isIgstApplicable;

    private BigDecimal netAmount;

    private String amountInWords;

    private String narration;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private String updatedBy;

    private String cancelRemarks;

    private String active;

    private String cancel;

    private String screenCode;

    private String screenName;

    private List<SalesReturnDetailsResponseDTO> salesReturnDetails;

    private List<SalesReturnTaxDetailsResponseDTO> salesReturnTaxDetails;
}