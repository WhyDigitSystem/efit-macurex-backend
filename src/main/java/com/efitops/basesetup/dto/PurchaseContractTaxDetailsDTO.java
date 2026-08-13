package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * "2-Tax Details" grid line — all three fields are direct user entry, no lookups.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseContractTaxDetailsDTO {



    // [USER ENTER]
    private String particulars;

    // [USER ENTER]
    private BigDecimal taxPercent;

    // [USER ENTER]
    private BigDecimal amount;
}