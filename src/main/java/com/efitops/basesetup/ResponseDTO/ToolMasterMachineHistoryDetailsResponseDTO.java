package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolMasterMachineHistoryDetailsResponseDTO {
    private Long id;

    private LocalDate date;

    private String description;

    private LocalDate changedDate;

    private BigDecimal cost;

    private String purpose;

    private String remarks;

}
