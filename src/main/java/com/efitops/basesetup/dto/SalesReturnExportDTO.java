package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReturnExportDTO {
	private Long id;
	private String customerName;
	private String salesOrderNo;
	private String salesInvoiceExportNo;
	private LocalDate salesInvoiceExportDate;
	private String exportPackingNo;
	private String currency;
	private Long exchangeRate;
	private String location;
	private String billingAddress;
	private String shippingAddress;

	private Long orgId;
	private String branch;
	private String branchCode;
	private String finYear;
	private String createdBy;
	private String remarks;

	private List<SalesReturnExportDetailsDTO> salesReturnExportDetailsDTO;

	private List<SalesReturnExportTermsDTO> salesReturnExportTermsDTO;

}
