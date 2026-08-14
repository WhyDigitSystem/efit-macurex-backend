package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.CurrencyResponseDTO;
import com.efitops.basesetup.entity.BankDetailsVO;
import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.CustomerVO;
import com.efitops.basesetup.entity.LocationVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProformaInvoiceResponseDTO {

	private Long id;

	private String docId;

	private LocalDate docDate;

	private String belongsTo;

	private CustomerOtherSalesResponseDTO customer;

	private String purchaseOrderNo;

	private LocalDate purchaseOrderDate;

	private String refNo;

	private LocalDate refDate;

	private String kindAttention;

	private String designation;

	private LocationMasterResponseDTO location;

	private LocalTime timeOfIssue;

	private LocalDate timeOfIssueDate;

	private BankResponseDetailsDTO bankName;

	private BigDecimal insurance;

	private BigDecimal freight;

	private BigDecimal noOfPkg;

	private String pkgType;

	private BigDecimal rateOfDuty;

	private String tariffNo;

	private BigDecimal basicValue;

	private BigDecimal grossAmount;

	private String modeOfTransport;

	private String amountInWords;

	private String deliveryTo;

	private String paymentTerms;

	private String paymentPercentage;

	private String createdBy;

	private String narration;

	private String active;

	private String cancel ;

	private String updatedBy;

	private String cancelRemarks;

	private String screenName;

	private String screenCode;

	private Long orgId;

	private String financialYear;

	private BranchResponseDTO branch;

    private List<ProformaInvoiceDetailsResponseDTO> proformaInvoiceDetailsResponseDTO;

    private List<ProformaInvoiceTaxDetailsResponseDTO> proformaInvoiceTaxDetailsResponseDTO;
}