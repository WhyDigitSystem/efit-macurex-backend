package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.efitops.basesetup.dto.BranchResponseDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectPurchaseResponseDTO {

	private Long id;
	private String docId;
	private LocalDate docDate;
	private GSTStateMasterResponseDTO gstState;
	private String belongsTo;
	private String supplierName;
	private LocalDate invDate;
	private String isIgstApplicable;
	private String issueTo;
	private String gstnNo;
	private String invNo;
	private String suppType;
	private String dealerType;
	private ItemCategoryResponseDTO itemCategory;
	private String eccNoStNo;
	private String isReverseCharge;

	private BigDecimal basicAmount;
	private BigDecimal discount;
	private BigDecimal afterDiscountTotalAmount;
	private BigDecimal totalAmount;

	private EmployeeMasterResponseDetailsDTO preparedBy;
	private String remarks;

	private String createdBy;
	private String active;
	private String cancel;
	private String updatedBy;
	private String cancelRemarks;
	private String screenName;
	private String screenCode;
	private Long orgId;
	private String financialYear;
	private BranchResponseDTO branch;

	private List<DirectPurchaseCashDetailsResponseDTO> directPurchaseCashDetails;
	private List<DirectPurchaseTaxDetailsResponseDTO> directPurchaseTaxDetails;
	private List<DirectPurchaseFileUploadDetailsResponseDTO> directPurchaseFileUploadDetails;
}
