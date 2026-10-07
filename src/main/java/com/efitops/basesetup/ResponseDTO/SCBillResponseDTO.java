package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.SCBillDetailDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SCBillResponseDTO {
	
	
	 private Long id;

	    // Header
	    private BranchResponseDTO branch;

	    private String docId;

	    private LocalDate docDate;

	    private DepartmentResponseDTO department;

	    private CustomerResponse1DTO vendorName;

	    private String vendorId;

	    private String gstState;

	    private Boolean isGstApplicable;

	    private String vendorInvoiceNo;

	    private String vendorDcNo;

	    private String gstnNo;

	    private LocalDate vendorInvoiceDate;

	    private String serviceName;

	    private String grnNo;

	    private String hsnSacCode;

	    private String contractNo;

	    private String taxType;

	    private BigDecimal taxPercentage;

	    private String belongsTo;

	    // Summary
	    private BigDecimal freight;

	    private BigDecimal totalAmount;

	    private BigDecimal totalBasic;

	    private String tdsApplicable;

	    private BigDecimal tdsPercentage;

	    private BigDecimal acceptedVal;

	    private BigDecimal rejectedVal;

	    private BigDecimal tdsAmount;

	    private String amountInWords;

	    private String remarks;

	    // Default fields
	    private boolean active;

	    private Long orgId;

	    private String financialYear;

	    private String createdBy;

	    private String updatedBy;

	    private boolean cancel;

	    private String cancelRemarks;
	    
	    
	    private List<SCBillDetailResponseDTO>sCBillDetailResponseDTO;
	    
	    private List<SCBillTaxGridResponseDTO>sCBillTaxGridResponseDTO;

}
