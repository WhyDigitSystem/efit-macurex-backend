package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    private String debitCredit;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private Boolean postToFinanceAc;
}