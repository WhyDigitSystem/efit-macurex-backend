package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalPurchaseOrderTaxDetailsResponseDTO {

    private Long id;
    private String particulars;
    private BigDecimal taxPercent;
    private BigDecimal amount;
}