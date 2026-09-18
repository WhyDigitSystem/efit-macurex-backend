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
public class DeliveryChallanCumGatePassDTO {

    private Long id;

    private String belongsTo;

    private String type;

    private boolean isIGSTAppl;

    private Long department;

    private String gstnNo;

    private Long partyPlantId;

    private Long fromLocation;

    private String modeOfTransport;

    private String vehicleNo;

    private Long workOrderNo;

    private BigDecimal totalQty;

    private Long preparedBy;

    private String remarks;

    // Common Fields

    private String createdBy;

    private boolean active;

    private Long orgId;

    private String financialYear;

    private String cancelRemarks;

    private Long branch;

    private List<DeliveryChallanCumGatePassDetailsDTO> deliveryChallanCumGatePassDetailsDTO;
}