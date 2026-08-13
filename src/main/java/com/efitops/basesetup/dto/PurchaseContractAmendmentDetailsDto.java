package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseContractAmendmentDetailsDto {

    private Long id;

    // Item (from Item Master)
    private Long item;

    // Unit (from Unit Master)
    private Long unit;

    // Rates
    private BigDecimal oldRate;
    private BigDecimal newRate;

    // Validity
    private LocalDate validFrom;
    private LocalDate newValidFrom;
    private LocalDate validTo;
    private LocalDate newValidTo;
}