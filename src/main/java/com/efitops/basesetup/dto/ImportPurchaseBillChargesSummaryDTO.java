package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportPurchaseBillChargesSummaryDTO {

    private BigDecimal totFobValueFc;

    private BigDecimal totFobValueInr;

    private BigDecimal netAmount;

    private BigDecimal totFriInsFc;

    private BigDecimal totDutyInr;

    private Boolean postVoucher;

    private BigDecimal totalValueFc;

    private BigDecimal totFreInsInr;

    private BigDecimal totLandCost;

    private String amountInWords;

    private String narration;
}