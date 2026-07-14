package com.efitops.basesetup.dto;

import java.time.LocalDate;

import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDTO {

	private Long id;

	private String customerName;

	private String customerCode;

	private String currency;

	private Long exChangeRate;

	private String customerPoNo;

	private String workOrderNo;

	private String shippingAddress;

	private String billingAddress;

	private String contactPerson;

	private String customerMail;

	private String placeOfSupply;

	private String taxType;

	private String invoiceType;

	private LocalDate dueDate;

	private String description;

	private String narration;

	private String createdBy;

	private Long orgId;

	private String branch;

	private String branchCode;

	private String finYear;

	private List<SalesItemParticularsDTO> salesItemParticularsDTO;

	private List<SalesOrderTermsDTO> salesOrderTermsDTO;

}
