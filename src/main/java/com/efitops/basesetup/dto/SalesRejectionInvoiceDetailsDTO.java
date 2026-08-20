package com.efitops.basesetup.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesRejectionInvoiceDetailsDTO {

//    private Long id;


    // =========================
    // COMMON FIELDS
    // =========================

    private BigDecimal newRate;

    private Long item;

    private String hsnSacCode;

    private String taxType;

    private BigDecimal taxPercentage;

    private String customerPartNo;

    private Long unit;


    private String stock;

    private String salesOrderContractNo;

    private BigDecimal despatchQty;


//    private BigDecimal rateInSelectedCurrency;
//
//    private BigDecimal amountInSelectedCurrency;
//
//    private BigDecimal amountInRs;


    // =========================
    // SGST
    // =========================

    private BigDecimal sgstRate;

//    private BigDecimal sgstAmount;


    // =========================
    // CGST
    // =========================

    private BigDecimal cgstRate;

//    private BigDecimal cgstAmount;


    // =========================
    // IGST
    // =========================

    private BigDecimal igstRate;

//    private BigDecimal igstAmount;

}