package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderShortCloseDetailsResponseDTO {

    private Long id;

    private JOShortCloseItemResponseDTO item;

    private BigDecimal orderQty;

    private BigDecimal suppliedQty;

    private BigDecimal pendingQty;

    private BigDecimal requiredQty;

    private BigDecimal shortCloseQty;
}