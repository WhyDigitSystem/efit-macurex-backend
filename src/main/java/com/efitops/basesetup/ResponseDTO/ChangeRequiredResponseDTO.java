package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRequiredResponseDTO {
    private Long id;
    private String fixtures;
    private String anyChanges;
    private BigDecimal estimatedCost;
    private BigDecimal leadTime;
}