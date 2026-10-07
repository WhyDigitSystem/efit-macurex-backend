package com.efitops.basesetup.dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReworkReasonDTO {
    private Long reason;
    private String reasonDescription;
    private BigDecimal qty;
    private BigDecimal timePerQty;
    private BigDecimal reworkMcCost;
    private BigDecimal reworkLabourCost;
}