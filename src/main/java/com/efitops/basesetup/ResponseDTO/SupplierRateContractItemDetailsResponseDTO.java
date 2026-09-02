package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractItemDetailsResponseDTO {

    private Long id;

    private ItemMasterResponseDetailsDTO incomingItemCode;

    private String platingType;

    private BigDecimal thickness;

    private BigDecimal rate;

    private BigDecimal sgstRate;

    private BigDecimal sgstAmount;

    private BigDecimal cgstRate;

    private BigDecimal cgstAmount;

    private BigDecimal igstRate;

    private BigDecimal igstAmount;

    private LocalDate validFrom;

    private LocalDate validTo;

    private BigDecimal toolAmortizationRate;

}