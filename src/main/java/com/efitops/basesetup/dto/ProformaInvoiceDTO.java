package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProformaInvoiceDTO {
	private Long id;

	private Long customer;

	private String belongsTo;

	private String purchaseOrderNo;

	private LocalDate purchaseOrderDate;

	private String refNo;

	private LocalDate refDate;

	private String kindAttention;

	private String designation;

	private Long location;

	private Long bankName;

	private BigDecimal insurance;

	private BigDecimal freight;

	private BigDecimal noOfPkg;

	private String pkgType;

	private BigDecimal rateOfDuty;

	private String tariffNo;

	private String modeOfTransport;

	private String deliveryTo;

	private String paymentTerms;

	private String paymentPercentage;

	private String createdBy;

	private String narration;

	private boolean active;

	private String cancelRemarks;

	private String isIgstApplicable;

	private Long orgId;

	private String financialYear;

	private Long branch;

	private List<ProformaInvoiceDetailsDTO> proformaInvoiceDetailsDTO;

	private List<ProformaInvoiceTaxDetailsDTO> proformaInvoiceTaxDetailsDTO;

}
