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
public class FlashNCReportDTO {

	private Long id;

	private Long branch;

	private Long belongsTo;

	private Long reference;

	private Long fromDept;

	private Long toDept;

	private String Description;

	private String drawingNo;

	private String mrinSCGRNNO;

	private LocalDate mrinDate;

	private BigDecimal occPercentage;

	private String invoiceNo;

	private String poNo;

	private Long supplier;

	private String operationNo;

	private Long item;

	private BigDecimal lotQty;

	private BigDecimal sampleQty;

	private BigDecimal ncQty;

	private Long disposal;

	private String defectSeen;

	private String problemStatus;

	private String actionOnDefectiveLot;

	private Long inspectedBy;

	private Long status;

	private String narration;

	private Long orgId;

	private String financialYear;

	private boolean active;

	private String cancelRemarks;

	private String createdBy;

	private List<FlashNCReportAttachmentDTO>flashNCReportAttachmentDTO;

}
