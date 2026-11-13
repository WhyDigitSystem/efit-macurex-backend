package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReturnDetailsResponseDTO {

    private Long id;

    private ItemResponse1DTO item;

    private String hsnSacCode;

    private String taxType;

    private String taxPercentage;

    private UnitMasterResponseDTO unit;

    private BigDecimal stock;

    private BigDecimal qtySold;

    private BigDecimal receivedQty;

    private BigDecimal rate;

    private BigDecimal rateInSelectedCurrency;

    private BigDecimal amountInSelectedCurrency;

    private BigDecimal amount;

    private BigDecimal igstRate;

    private BigDecimal igstAmount;

    private BigDecimal cgstRate;

    private BigDecimal cgstAmount;

    private BigDecimal sgstRate;

    private BigDecimal sgstAmount;
}
