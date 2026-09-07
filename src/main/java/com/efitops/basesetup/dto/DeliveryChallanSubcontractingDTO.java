package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryChallanSubcontractingDTO {

    private Long id;

    private Long branch;

    private Long department;

    private String belongsTo;

    private Long vendor;

    private Long partyLocation;

    private String jobOrderNo;

    private Long incomingItem;

    private Long transportName;

    private String vehicleNo;

    private Long sfgBomId;

    private BigDecimal qty;

    private String timeOfIssue;

    private String dcType;

    private Long preparedBy;

    private Long approvedBy;

    private String approvalByStores;

    private String remarks;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private List<DeliveryChallanSubcontractingDetailsDTO> details;
}