package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryChallanSubcontractingDetailsDTO {


    private Long outgoingItem;

    private BigDecimal stock;

    private Long unit;

    private Long fromLocation;

    private BigDecimal availableStock;

    private BigDecimal issueQty;

    private BigDecimal unitRate;

//    private BigDecimal amount;

    private String remarks;
}
