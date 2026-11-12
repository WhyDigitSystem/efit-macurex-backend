package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionOrderDetailsResponseDTO {
    private Long id;
    private String scheduleOrderNo;
    private LocalDate scheduleDate;
    private BigDecimal scheduleOrderQty;
    private BigDecimal balanceQty;
    private BigDecimal newReqQty;
    private BigDecimal shortClosedQty;
    private String reason;
}