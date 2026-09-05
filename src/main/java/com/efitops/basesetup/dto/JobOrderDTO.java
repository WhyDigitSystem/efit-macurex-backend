package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOrderDTO {

	private Long id;

	// =========================
	// PLANT / BRANCH
	// =========================

	private Long branch;

	private Long department;

	private String belongsTo;

	// =========================
	// VENDOR DETAILS
	// =========================

	private Long vendor;

	private Long gstState;

	// =========================
	// JOB ORDER FOR
	// =========================

	private String jobOrderFor;

	// =========================
	// GST
	// =========================

	private boolean isIgstAppl;

	// =========================
	// CONTRACT
	// =========================

	private String contractNo;

	// =========================
	// SERVICE DETAILS
	// =========================

	private Long serviceName;

	private String indentTime;

	// =========================
	// TAX DETAILS
	// =========================

	private Long hsnSacCode;

	private String taxType;

	private BigDecimal taxPercentage;

	// =========================
	// PAYMENT / DELIVERY
	// =========================

	private String paymentTerms;

	private LocalDate deliveryDate;

	// =========================
	// AMOUNT
	// =========================

	private BigDecimal amount;

	// =========================
	// NARRATION / NOTE
	// =========================

	private String narration;

	private String note;

	// =========================
	// COMMON FIELDS
	// =========================

	private Long orgId;

	private String financialYear;

	private String createdBy;

	private boolean active;

	private String cancelRemarks;

	private List<JobOrderDetailsDTO> jobOrderDetails;

	private List<JobOrderTaxDetailsDTO> jobOrderTaxDetails;

	private List<JobOrderAttachmentDTO> attachments;
}
