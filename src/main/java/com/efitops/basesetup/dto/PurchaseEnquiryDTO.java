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
public class PurchaseEnquiryDTO {
	
	private Long id;
    private String customerName;
	private String customerCode;
    private String workOrderNo;
    private String purchaseIndentNo;
    private String customerPoNo;
    private String fgPartName;
    private String fgPartDesc;
    private String supplierName;
	private String supplierCode;
    private String contactPerson;
    private String contactNo;
    private String enquiryType;
    private LocalDate enquiryDueDate;
    private LocalDate expectedDeliveryDate;
    private String summary;
 	private String branch;
 	private String branchCode;
 	private String finYear;

 	private	 String createdBy;
 	private String cancelRemarks;
 	private Long orgId;
    
 	private List<PurchaseEnquiryDetailsDTO> purchaseEnquiryDetailsDTO;
}
