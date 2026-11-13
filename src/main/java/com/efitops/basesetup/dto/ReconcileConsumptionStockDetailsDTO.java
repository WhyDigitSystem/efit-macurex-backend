package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReconcileConsumptionStockDetailsDTO {


    private Long item;

    private Long unit;

    private BigDecimal availableQty;

    private BigDecimal consumptionQty;

    private BigDecimal postedQty;

    private BigDecimal differenceQty;

    private BigDecimal rate;

//    private BigDecimal value;
}