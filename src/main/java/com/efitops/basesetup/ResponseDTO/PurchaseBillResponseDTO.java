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
    private String docId;
    private String belongsTo;
    private LocalDate docDate;
    private PurchaseBillSupplierResponseDTO supplier;
    private String grnNo;
    private LocalDate grnDate;
    private Boolean isIgstAppl;
    private Boolean excisable;
    private String vendorDcNo;
    private BigDecimal exchangeRate;
    private ListOfVlauesDetailsResponseDTO dealerType;
    private String purchaseorderType;
    private Long purchaseorderId;
    private String purchaseorderNo;
    private LocalDate purchaseorderDate;
    private Boolean isReverseChrg;
    private LocalDate voucherPostingDate;
    private LocalDate date;
    private BigDecimal dutyPerUnit;
    private ListOfVlauesDetailsResponseDTO postingCategory;
    private Boolean modvatCopyReceived;
   
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
    
//   purchase Import bill
    private String creditAcc;
	private Long statutoryForms;
	private String supplierInvValue;
	
    private Long orgId;
    private String financialYear;
    private String active;
    private String cancelRemarks;
    private String createdBy;
    private String updatedBy;

//    private List<PurchaseBillDetailsResponseDTO> purchaseDetails;
//    private List<PurchaseBillTaxGridResponseDTO> taxGrid;

    
}