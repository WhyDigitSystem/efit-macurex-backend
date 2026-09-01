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
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferGrnResponseDTO {

    private Long id;
    private String docId;
    private LocalDate docDate;
    private String belongsTo;
    private String isIgstApplicable;
    private String isReverseCharge;
    private String gatePassNo;
    private String poNo;
    private String dealerType;
    private String scheduleNo;
    private LocalDate scheduleDate;
    private LocalDate scheduleStartDate;
    private LocalDate scheduleEndDate;
    private BigDecimal exchangeRate;
    private LocalTime grnClearTime;
    private BigDecimal grossAmount;
    private String modvatCopyReceived;
    private BigDecimal totalQtyInKg;
    private String partyDcNo;
    private BigDecimal discount;
    private String supplierDcDate;
    private String createdBy;
    private String updatedBy;
    private String active;
    private String cancel;
    private String cancelRemarks;
    private Long orgId;
    private String financialYear;
    private BigDecimal netAmount;
    private BigDecimal totalAmountTax;
    private BigDecimal basicAmount;
    private LocalDate invoiceSentOn;
    private String remarks;
    private String screenName;
    private String screenCode;


    private BranchResponseDTO branch;
    private LocationMasterResponseDTO location;
    private SupplierResponseDTO supplierCode;
    private CurrencyResponseDTO currency;
    
    private List<StockTransferGrnDetailsResponseDTO> stockTransferGrnDetailsResponseDTO;
    private List<StockTransferGrnFileUploadDetailsResponseDTO> stockTransferGrnFileUploadDetailsResponseDTO;
}
