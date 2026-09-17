package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractingGRNConsumptionDTO {


    private Long outgoingItem;

    private Long unit;

    private String itemType;

    private BigDecimal bomQty;

    private BigDecimal availableStock;

//    private BigDecimal consumedQty;

    private String scrapItem;

    private BigDecimal bomScrap;

    private BigDecimal scrapQty;

    private BigDecimal rate;

    private BigDecimal amount;

}