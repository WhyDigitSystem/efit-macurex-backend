package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDTO {
	
	private Long id;
	private String customerName;
	private String customerCode;
	private String workOrderNo;
	private String basedOn;
	private String quotationNo;
	private String purchaseIndentNo; 
	private String supplierName;
	private String supplierCode;
	private String contactPerson;
	private long   mobileNo;
	private String email;
	private String state;
	private String city;
	private String country; 
	private String taxCode; 
	private String address; 
	private String remarks;
	private String branch;
	private String branchCode;
	private String finYear;
	private boolean poPendingCancel;
	private Long orgId;
	private String createdBy;
	private boolean active;
	private boolean cancel;
	
	List<PurchaseOrderDetailsDTO> purchaseOrderDetailsDTO;

}
