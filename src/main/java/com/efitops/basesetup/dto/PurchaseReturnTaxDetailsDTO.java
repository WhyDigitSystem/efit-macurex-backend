package com.efitops.basesetup.dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseReturnTaxDetailsDTO {
    private String particulars;
    private BigDecimal tax;
    private BigDecimal acceptedQtyAmount;
    private BigDecimal revisedAmount;
}