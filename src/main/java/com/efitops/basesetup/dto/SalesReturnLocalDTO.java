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
public class SalesReturnLocalDTO {
	private Long id;
	private String customerName;
	private String salesInvoiceLocalNo;
	private LocalDate salesInvoiceLocalDate;
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

	List<SalesReturnLocalDetailsDTO> salesReturnLocalDetailsDTO;
}
