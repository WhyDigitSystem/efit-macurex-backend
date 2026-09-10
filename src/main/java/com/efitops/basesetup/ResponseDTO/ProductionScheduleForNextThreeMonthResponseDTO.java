package com.efitops.basesetup.ResponseDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionScheduleForNextThreeMonthResponseDTO {

    private Long id;

    private String monthYear;

    private Long branch;

    private Long orgId;

    private String financialYear;

    private String createdBy;

    private boolean active;

    private String cancelRemarks;

    private List<ProductionScheduleForNextThreeMonthDetailsResponseDTO>
            productionScheduleForNextThreeMonthDetails;
}