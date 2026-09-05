package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOrderDetailsDTO {

//    private Long id;

    // Incoming Item
    private Long incomingItem;

    // BOM
//    private Long bom;

    private String bom;

    // Unit
    private Long unit;

    // Incoming Type
    private String incomingType;

    // Quantity
    private BigDecimal orderQty;

    // Rate
    private BigDecimal rate;

    // Amount
    private BigDecimal amount;

    // SGST
    private BigDecimal sgstRate;

    // CGST

    // IGST
    private BigDecimal igstRate;
    private BigDecimal cgstRate;

    // Sent For
    private String sentFor;

}