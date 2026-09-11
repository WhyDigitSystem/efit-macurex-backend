package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChallanCumGatePassDetailsDTO {

    private Long item;

    private Long hsnSacCode;

    private Long unit;

    private BigDecimal stock;

    private BigDecimal availableQty;

    private BigDecimal qty;

    private LocalDate dueDate;

    private BigDecimal previousQty;

    private BigDecimal lcRate;

    private BigDecimal rate;
}