package com.efitops.basesetup.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionScheduleForNextThreeMonthDTO {

    private Long id;

    private String monthYear;

    private Long branch;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private List<ProductionScheduleForNextThreeMonthDetailsDTO>
            productionScheduleForNextThreeMonthDetails;
}