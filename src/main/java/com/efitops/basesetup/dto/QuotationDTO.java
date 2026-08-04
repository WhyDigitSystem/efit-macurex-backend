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
public class QuotationDTO {

	private Long id;

	private String userCategory;

//	private Long plantId;

	private Long customer;

	private String withEnquiry;

	private String partyName;

	private BigDecimal amount;

	private String oldEnquryNo;

	private String enquiryNo;

	private LocalDate enquiryDate;

	private String enquiryControl;

	private String reason;

	private String preparedBy;

	private String quotationSerialNo;


	private String customerEnquiryNo;

	private String customerEnquiryDate;

	private String enqBasicId;

	private Long validTill;

	private LocalDate date;
	

	private String kindAttention;

	// Common Fields

	private String createdBy;

	private boolean active = true;

	private boolean cancel = false;

	private String updatedBy;

	private String cancelRemarks;

	private String screenName;

	private String screenCode;

	private Long orgId;

	private String financialYear;

	private Long branchId;


	private BigDecimal freight;

	private String freightBy;

	private BigDecimal totalAmount;

	private String terms;

	private String remarks;

	private List<QuotationItemDetailsDTO> quotationItemDetailsDTO;

	private List<QuotationItemTaxDetailsDTO> quotationItemTaxDetailsDTO;

//	private List<QuotationIemFileUploadDetailsDTO> quotationIemFileUploadDetailsDTO;

}
