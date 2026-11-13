package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseReturnDTO {

    private Long id;
    private Long branch;
    private String belongsTo;
    private Long supplier;
    private String grnNo;
    private LocalDate grnDate;
    private String isIgstAppl;
    private Boolean excisable;
    private String vendorDcNo;
    private BigDecimal exchangeRate;
    private String dealerType;
    private String purchaseorderNumber;
    private String purchaseorderType;
    private LocalDate purchaseorderDate;
    private Boolean isReverseChrg;
    private LocalDate voucherPostingDate;
    private BigDecimal dutyPerUnit;
    private Boolean modvatCopyReceived;
    private String supplierDcInvNo;
    private LocalDate supplierDcInvDate;
    private Boolean entryTaxApplicable;
    private String narration;
    private String paymentTerms;

    private BigDecimal totalFreight;
    private BigDecimal basicValue;


    // Audit
    private Long orgId;
    private String financialYear;
    private boolean active;
    private boolean cancel;
    private String cancelRemarks;
    private String createdBy;

    private List<PurchaseReturnDetailsDTO> purchaseReturnDetailsDTO;
    private List<PurchaseReturnTaxDetailsDTO> purchaseReturnTaxDetailsDTO;
}
