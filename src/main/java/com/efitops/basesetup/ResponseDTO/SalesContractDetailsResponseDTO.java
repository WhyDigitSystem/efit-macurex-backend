package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractDetailsResponseDTO {

    private Long id;

    private SalesContractItemResponseDTO item;

    private String taxType;

    private GSTRateResponseDTO taxPercentage;

    private UnitResponseDTO unit;

    private BigDecimal quantity;

    private BigDecimal quotationRate;

    private BigDecimal orderRate;

    private BigDecimal discountPercentage;

    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;

    private BigDecimal discountAmount;

    private BigDecimal amount;

    private BigDecimal sgstRate;

    private BigDecimal sgstAmount;

    private BigDecimal cgstRate;

    private BigDecimal cgstAmount;

    private BigDecimal igstRate;

    private BigDecimal igstAmount;

//    private BigDecimal finalAmount;

    private String currency;
}
