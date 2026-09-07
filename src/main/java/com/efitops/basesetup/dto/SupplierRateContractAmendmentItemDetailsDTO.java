package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractAmendmentItemDetailsDTO {

    private Long id;

    // Item Code
    private Long item;

    // Unit
    private Long unit;

    // Old Rate
    private BigDecimal oldRate;

    // New Rate
    private BigDecimal newRate;
}