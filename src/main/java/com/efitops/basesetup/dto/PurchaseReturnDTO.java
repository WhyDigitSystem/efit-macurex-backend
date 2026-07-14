package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReturnDTO {
	private Long id;
	private String supplierName;
	private String supplierCode;
	private String customerName;
	private String customerCode;
	private String purchaseInvoiceNo;
	private LocalDate purchaseInvoiceDate;
	private String poNo;
	private String gstNo;
	private int gstState;
	private boolean poPendingCancel;
	private String address;
	private String gatePassNo;
	private String isReverseChrg;
	private String currency;
	private BigDecimal exchangeRate;
	private String invDcNo;
	private LocalDate invDcDate;
	private String gstType;
	private String toLocation;
    private String remarks;	
    private String status;
	private Long orgId;
	private String createdBy;
	private boolean active;
	private String branch;
	private String branchCode;
	private String finYear;
	
private List<PurchaseReturnItemDTO>purchaseReturnItemDTO;
	
	
}
