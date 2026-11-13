package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.ResponseDTO.QuotationIemFileUploadDetailsDTO;
import com.efitops.basesetup.service.CustomerResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationResponseDTO {

	private Long id;

	private String userCategory;

	private String docId;

	private LocalDate docDate;

//	private Long plantId;

//	private BranchResponseDTO plant;

	private Long partyId;

	private CustomerResponseDetailsDTO customer;

	private String withEnquiry;

//	private String partyName;

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

	private LocalDate validTill;

	private LocalDate date;

	private String kindAttention;

	// Common Fields

	private String createdBy;

	private String updatedBy;

	private boolean active;

	private boolean cancel;

	private String cancelRemarks;

	private String screenName;

	private String screenCode;

	private Long orgId;

	private String financialYear;

//	private BranchResponseDTO branchId;

	private BranchResponseDTO branch;

	private BigDecimal amount;

	private BigDecimal freight;

	private String freightBy;

	private BigDecimal totalAmount;

	private String terms;

	private String remarks;

	private List<QuotationItemDetailsResponseDTO> quotationItemDetailsResponseDTO;

	private List<QuotationItemTaxDetailsDTO> quotationItemTaxDetailsDTO;

	private List<QuotationIemFileUploadDetailsDTO> quotationIemFileUploadDetailsDTO;

}