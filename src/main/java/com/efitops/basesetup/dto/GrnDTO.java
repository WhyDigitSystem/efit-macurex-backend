package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnDTO {

	private Long id;

	private String belongsTo;

	private Long location;

	private Long supplierCode;

	private String isIgstApplicable;

	private String isReverseCharge;

	private String gatePassNo;

	private String poNo;

	private String dealerType;

	private String scheduleNo;

	private LocalDate scheduleDate;

	private LocalDate scheduleStartDate;

	private LocalDate scheduleEndDate;

	private Long currency;

	private BigDecimal exchangeRate;

	private BigDecimal grossAmount;

	private String modvatCopyReceived;

	private BigDecimal totalQtyInKg;

	private String partyDcNo;

	private BigDecimal discount;

	private String supplierDcDate;

	private String createdBy;

	private boolean active;

	private boolean cancel;

	private String updatedBy;

	private String cancelRemarks;

	private Long orgId;

	private String financialYear;

	private Long branch;

	private BigDecimal totalAmountTax;

	private LocalDate invoiceSentOn;

	private String remarks;

	private String grnType;

	// import grn details

	private String shipmentNo;

	private LocalDate shipmentDate;

	private String blNo;

	private LocalDate blDate;

	private Long transporter;

	private LocalDate poDate;

	private String vehicleNo;

	private String invoiceNo;

	private LocalDate invoiceDate;

	private String poCurrency;

	private String lrNo;

	private BigDecimal poExchangeRate;

	// grnimportsummary

	private String receivedBy;

	private String qualityCheckBy;

	private List<GrnDetailsDTO> grnDetailsDTO;

	private List<GrnTaxDetailsDTO> grnTaxDetailsDTO;

	private List<ImportGrnDetailsDTO> importGrnDetailsDTO;
}
