package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractSupplyScheduleDetailsDTO {

    private Long id;

    // =========================
    // Schedule Details
    // =========================

    private LocalDate planDate;

    private BigDecimal scheduleQty;
}