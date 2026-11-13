package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReconcileConsumptionStockDTO {

    private Long id;

    private Long branch;

    private LocalDate reconcileDate;

    private Long shopFloor;

    private Long fgItem;

    private Long rmLocation;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private Long orgId;

    private String financialYear;

    private List<ReconcileConsumptionStockDetailsDTO> details;
}