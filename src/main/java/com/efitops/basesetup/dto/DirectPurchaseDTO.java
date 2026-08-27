package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.efitops.basesetup.entity.BranchVO;
import com.efitops.basesetup.entity.EmployeeMasterVO;
import com.efitops.basesetup.entity.GSTStateMasterVO;
import com.efitops.basesetup.entity.ItemMasterVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectPurchaseDTO {

	private Long id;

	private Long gstState;

	private String belongsTo;

	private String supplierName;

	private LocalDate invDate;

	private String isIgstApplicable;

	private String issueTo;

	private String gstnNo;

	private String invNo;

	private String suppType;

	private String dealerType;

	private Long itemCategory;

	private String eccNoStNo;

	private String isReverseCharge;

	private Long preparedBy;

	private String remarks;

	private String createdBy;

	private boolean active;

	private String cancelRemarks;

	private Long orgId;

	private String financialYear;

	private Long branch;

	private BigDecimal basicAmount;

	private BigDecimal discount;

	private BigDecimal afterDiscountTotalAmount;

	private BigDecimal totalAmount;

	private List<DirectPurchaseCashDetailsDTO> directPurchaseCashDetailsDTO;

	private List<DirectPurchaseTaxDetailsDTO> directPurchaseTaxDetailsDTO;

}
