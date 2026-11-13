package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseContractDetailsDTO {



    private Long item;

    private String hsnCode;

    private String taxType;

    private String taxPercentage;

    private Long unit;

    private BigDecimal rateInCurrency;

    private BigDecimal sgstRate;

    private BigDecimal sgstAmount;

    private BigDecimal cgstRate;

    private BigDecimal cgstAmount;

    private BigDecimal igstRate;

    private BigDecimal igstAmount;

    private LocalDate validFrom;

    private LocalDate validTo;
}