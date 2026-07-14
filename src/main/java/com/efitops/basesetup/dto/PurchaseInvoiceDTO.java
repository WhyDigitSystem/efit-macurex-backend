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
public class PurchaseInvoiceDTO {
	private Long id;
	private String supplierName;
	private String poNo;
	private String grnNo;
	private LocalDate grnDate;
	private String location;
	private String inWardNo;
	private String supplierCode;
	private int gstState;
	private String gstNo;
	private String isReverseChrg;
	private String address;
	private String currency;
	private BigDecimal exchangeRate;
	private String invDcNo;
	private LocalDate invDcDate;
	private String gstType;
	private String customerName;
	private Long orgId;
	private String cnt;
	private String createdBy;
	private boolean active;
	private String remarks;
	private String branch;
	private String branchCode;
	private String finYear;

	private List<PurchaseInvoiceItemDTO> purchaseInvoiceItemDTO;
	
}
