package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesInvoiceLocalDTO {
	private Long id;
	private String customerName;
	private String packingListNo;
	private String salesOrderNo;
	private String gstNo;
	private String currency;
	private BigDecimal exchangeRate;
	private String location;
	private String billingAddress;
	private String shippingAddress;
	private String taxType;

	private String remarks;

	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private String terms;
	private String description;

	List<SalesInvoiceLocalDetailsDTO> salesInvoiceLocalDetailsDTO;

//	List<SalesInvoiceLocalTermsDTO> salesInvoiceLocalTermsDTO;

}
