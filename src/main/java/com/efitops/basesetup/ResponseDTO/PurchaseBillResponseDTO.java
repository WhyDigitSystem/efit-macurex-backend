// PurchaseBillResponseDTO.java
package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseBillResponseDTO {

    private Long id;
    private BranchResponseDTO branch;
    private String docId;
    private String belongsTo;

    private LocalDate docDate;
    private PurchaseBillSupplierResponseDTO supplier;
    private String grnNo;
    private LocalDate grnDate;
    private boolean isIgstAppl;
    private boolean excisable;
    private String vendorDcNo;
    private BigDecimal exchangeRate;
    private CurrencyResponseDTO currency;   // small {id, code, name} DTO, mirroring BranchResponseDTO

//    private ListOfVlauesDetailsResponseDTO dealerType;
    private String purchaseorderType;
    private String purchaseorderNo;
    private LocalDate purchaseorderDate;
    private boolean isReverseChrg;
    private LocalDate voucherPostingDate;
    private LocalDate date;
    private BigDecimal dutyPerUnit;
//    private ListOfVlauesDetailsResponseDTO postingCategory;
    private Boolean modvatCopyReceived;
   
    private String supplierDcInvNo;
    private LocalDate supplierDcInvDate;
    
//   purchase Import bill
    private String creditAcc;
	private String statutoryForms;

	private String supplierInvValue;
    private String taxStructureName;

	
    private Long orgId;
    private String financialYear;
    private String active;
    private String cancelRemarks;
    private String createdBy;
    private String updatedBy;

    private List<PurchaseBillDetailsResponseDTO> purchaseDetails;
    private List<PurchaseBillTaxGridResponseDTO> taxGrid;
    private List<PurchaseBillChargesSummaryResponseDTO> billChargesSummaryDTO;
    private List<ImportPurchaseBillDetailsResponseDTO> importPurchaseDetails;
    private List<ImportPurchaseBillTaxDetailsResponseDTO> importPurchaseTax;
    private List<ImportPurchaseBillChargesSummaryResponseDTO> importBillChargesSummaryDTO;

    
}