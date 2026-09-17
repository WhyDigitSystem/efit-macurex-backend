package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderShortCloseDetailsDTO {

    private Long id;

    private Long item;

    private BigDecimal orderQty;

    private BigDecimal suppliedQty;

    private BigDecimal pendingQty;

    private BigDecimal requiredQty;

    private BigDecimal shortCloseQty;
}
