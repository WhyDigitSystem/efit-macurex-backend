package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalPurchaseOrderTaxDetailsDTO {

    private Long id;
    private String particulars;
    private BigDecimal taxPercent;
    private BigDecimal amount;
}