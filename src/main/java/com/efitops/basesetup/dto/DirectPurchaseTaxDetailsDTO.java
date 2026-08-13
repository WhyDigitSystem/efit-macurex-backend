package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * particulars    -> [USER ENTER]
 * amount         -> [USER ENTER]
 * ledgerAccount  -> [USER SELECT] List Of Values, same pattern as Purchase Bill Tax Grid
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectPurchaseTaxDetailsDTO {

    private Long id;
    private String particulars;
    private BigDecimal amount;
    private Long ledgerAccount;
}