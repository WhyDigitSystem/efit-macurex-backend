package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionScheduleForNextThreeMonthDetailsDTO {


    private Long item;

    private LocalDate date;

    private BigDecimal january;

    private BigDecimal february;

    private BigDecimal march;

    private BigDecimal april;

    private BigDecimal may;

    private BigDecimal june;

    private BigDecimal july;

    private BigDecimal august;

    private BigDecimal september;

    private BigDecimal october;

    private BigDecimal november;

    private BigDecimal december;
}
