// PurchaseBillResponseDTO.java
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
public class PurchaseBillResponseDTO {

    private Long id;
    private BranchResponseDTO branch;
    private String pbNo;
    private String belongsTo;
    private LocalDate pbDate;
    private CustomerResponseDetailsDTO supplier;
    private GSTStateResponseDTO gstState;
    private String grnNo;
    private LocalDate grnDate;
    private Boolean isIgstAppl;
    private Boolean excisable;
    private ListOfVlauesDetailsResponseDTO currency;
    private String gstnNo;
    private String vendorDcNo;
    private BigDecimal exchangeRate;
    private ListOfVlauesDetailsResponseDTO dealerType;
    private ListOfVlauesDetailsResponseDTO taxCode;
    private String poType;
    private Long poId;
    private String poNo;
    private LocalDate poDate;
    private Boolean isReverseChrg;
    private LocalDate voucherPostingDate;
    private LocalDate date;
    private BigDecimal dutyPerUnit;
    private ListOfVlauesDetailsResponseDTO postingCategory;
    private Boolean modvatCopyReceived;
    private ListOfVlauesDetailsResponseDTO eccType;
    private String supplierDcInvNo;
    private LocalDate supplierDcInvDate;

    private BigDecimal totalFreight;
    private BigDecimal totalQty;
    private BigDecimal basicValue;
    private BigDecimal totalAmount;
    private String amountInWords;
    private Boolean entryTaxApplicable;
    private String narration;
    private String paymentTerms;

    private List<PurchaseBillDetailsResponseDTO> purchaseDetails;
    private List<PurchaseBillTaxGridResponseDTO> taxGrid;

    private Long orgId;
    private String financialYear;
    private Boolean active;
    private String cancelRemarks;
    private Long createdBy;
    private Long updatedBy;
}