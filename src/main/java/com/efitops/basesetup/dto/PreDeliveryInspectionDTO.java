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
public class PreDeliveryInspectionDTO {

	private Long id;
    private String transferSlipNo; 
    private LocalDate transferSlipDate; 
	private String belongsTo;
	private Long item; 
	private String itemDrawingNo;
	private String prodSchOrdNo;
	private Long fromLocation; // ID for LocationVO
	private BigDecimal stock;
	private String customerCode;
	private String lcDeliverySchNo;
	private String poNo;
	private String invNo;
	private LocalDate invDate;
	private String initialPlanNo;
	private LocalDate date;
	private LocalDate schOrdDate;
	private BigDecimal schQty;
	private BigDecimal producedQty;
	private String customerName;
	private String custPartNo;

	private BigDecimal qtyInspected;
	private BigDecimal qtyPassed;
	private Long toLocationId;
	private BigDecimal rate;
	private BigDecimal rejQty;
	private Long rejectedLocation;
	private String reasonForRejection;
	private BigDecimal scrapQty;
	private BigDecimal reworkQty;
	private String reasonForRework;
	private String remarks;
	private Long inspectedBy;
	private Long checkedBy;

	private String createdBy;
	private String updatedBy;
	private boolean active;
	private boolean cancel;
	private String cancelRemarks;
	private Long orgId;
	private String financialYear;
	private Long branch;

	private List<PreDeliveryInspectionDetailsDTO> inspectionDetails;

}