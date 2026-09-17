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
public class SubContractingGRNDTO {

    private Long id;

    private Long branch;

    private String belongsTo;

    private Long department;

    private Long vendor;

    private Long vendorLocation;

    private String gstState;

    private String gatePassNo;

    private Boolean isIGSTAppl;

    private String scheduleNo;

    private String gstnNo;

    private String rework;

    private String gstType;

    private boolean isRevsChrg;

    private LocalDate schStartDate;

    private Long serviceName;

    private LocalDate schEndDate;

    private Long sacCode;

    private String contractNo;

    private String taxType;

    private String supplierDcNo;

    private BigDecimal taxPercentage;

    private LocalDate supplierDcDate;

    private String grnClearTime;

    private BigDecimal basicAmount;

    private String remarks;

    private BigDecimal totalTax;

    private BigDecimal totalAmount;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private Long orgId;

    private String financialYear;

    private List<SubContractingGRNDetailsDTO> details;

    private List<SubContractingGRNTaxDetailsDTO> taxDetails;
}