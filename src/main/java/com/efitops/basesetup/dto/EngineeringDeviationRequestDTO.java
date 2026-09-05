package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngineeringDeviationRequestDTO {
	private Long id;

    private String docId;

    private LocalDate docDate;

    private Long toDepartment;

    private Long deviationRequestedBy;

    private String partDescription;

    private String customerId;

    private String productName;

    private BigDecimal quantityReceived;

    private String supplier;

    private Long deviationRequistApprovedBy;

    private String partNo;

    private String invoiceNo;

    private String descriptionOfNC;

    private String reasonForDeviationRequest;

    private String actionOnNC;

    private String deviationPeriod;

    private Long responsibleForName;

    private Long department;

    private String willTheNCAffectTheFit;

    private String willTheNCAffectTheForm;

    private String willTheNCAffectTheFunction; 

    private String willTheNCAffectTheSafety;

    private String natureOfTheDeviationRequest;

    private String toBeIntimatedToCustomerAndActionOnCustomerFeedBack;

    private String note;

    private Long productionMgr;

    private String productionMgrDisposition;

    private Long qualityMgr;

    private String qualityMgrDisposition;

    private Long tDCMgr;

    private String tdcMgrDisposition;

    private Long directorTechnical;

    private String directorTechnicalDisposition;

    private Long purMgr;

    private String purMgrDisposition;

    private String customerIntimationModeAndReference;

    private String customerFeedBack;

    private String customerFeedBackModeAndReference;

    private String decision;


}
