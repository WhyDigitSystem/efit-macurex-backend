package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesRejectionInvoiceDetailsResponseDTO {

    private Long id;

    private BigDecimal newRate;

   
    private ItemResponse1DTO item;

    private String hsnSacCode;

    private String taxType;

    private BigDecimal taxPercentage;

    private String customerPartNo;

    private UnitMasterResponseDTO unit;

    private String stock;

    private String salesOrderContractNo;

    private BigDecimal despatchQty;

    private BigDecimal rateInSelectedCurrency;

    private BigDecimal amountInSelectedCurrency;

    private BigDecimal amountInRs;

    private BigDecimal sgstRate;

    private BigDecimal sgstAmount;

    private BigDecimal cgstRate;

    private BigDecimal cgstAmount;

    private BigDecimal igstRate;

    private BigDecimal igstAmount;
}