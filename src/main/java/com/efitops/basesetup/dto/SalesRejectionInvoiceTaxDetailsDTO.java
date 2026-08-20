package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesRejectionInvoiceTaxDetailsDTO {



    // =========================
    // COMMON FIELDS
    // =========================

    private Long particulars;

    private String glAccountName;


    // =========================
    // SALES / REJECTION INVOICE
    // =========================

    private BigDecimal acceptedQtyAmount;

    private BigDecimal revisedAmount;


    // =========================
    // DC CUM INVOICE
    // =========================

    private BigDecimal amount;
}