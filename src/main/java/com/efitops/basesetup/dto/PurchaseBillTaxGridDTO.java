package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * particulars        -> [USER ENTER]
 * taxPercent         -> [USER ENTER]
 * acceptedQtyAmount  -> [CALCULATED or USER ENTER] ** CONFIRM
 * revisedAmount      -> [USER ENTER]
 * ledgerAccount      -> [USER SELECT] ** NEEDS a Chart of Accounts / Ledger master - CONFIRM table name
 * dbCr               -> [USER SELECT] "DB" or "CR" toggle
 * dbAmt / crAmt      -> [USER ENTER]
 * postToFinanceAc    -> [USER SELECT] Yes/No - whether this line posts to the finance ledger
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseBillTaxGridDTO {

    private Long id;
    private String particulars;
    private BigDecimal taxPercent;
    private BigDecimal acceptedQtyAmount;
    private BigDecimal revisedAmount;

    private Long ledgerAccount;
    private String dbCr;
    private BigDecimal dbAmt;
    private BigDecimal crAmt;
    private Boolean postToFinanceAc;
}