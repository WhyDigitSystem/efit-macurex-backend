package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessChangesResponseDTO {
    private Long id;
    private String processChange;
    private String layOut;
    private String actions;
    private BigDecimal estimatedCost;
    private BigDecimal leadTime;
}