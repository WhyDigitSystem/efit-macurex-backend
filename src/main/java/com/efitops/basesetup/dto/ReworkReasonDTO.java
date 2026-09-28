package com.efitops.basesetup.dto;


import java.math.BigDecimal;

import lombok.Data;

@Data
public class ReworkReasonDTO {
    private String reason;
    private String reasonDescription;
    private BigDecimal qty;
    private BigDecimal timePerQty;
    private BigDecimal reworkMcCost;
    private BigDecimal reworkLabourCost;
}