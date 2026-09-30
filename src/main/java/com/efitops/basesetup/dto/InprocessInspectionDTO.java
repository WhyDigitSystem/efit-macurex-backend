package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InprocessInspectionDTO {

    private Long id;

    private Long branch;

    private Long department;

    private Long controlPlan;

    private Long customer;

    private Long operationNo;

    private String specification;

    private Long partNo;

    private Long fromLocation;

    private BigDecimal stock;

    private Long machineNo;

    private Long shift;

    private BigDecimal producedQty;

    private BigDecimal acceptedQty;

    private BigDecimal rejectedQty;

    private BigDecimal rewQty;

    private Long inspectedBy;

    private Long verifiedBy;

    private Long toLocation;

    private String ncDetail;

    private String actionTaken;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private Long orgId;

    private String financialYear;

    private List<InprocessInspectionDetailsDTO> details;
}
