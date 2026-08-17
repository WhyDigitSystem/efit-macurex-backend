package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.UomConversionResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentDetailsResponseDTO {

    private Long id;

    // Item
    private PurchaseIndentItemResponseDTO item;

    // Quantity
    private BigDecimal qtyInPrimaryUnit;

    // Conversion Factor
    private UomConversionResponseDTO conversionFactor;

    // Purchase Quantity
    private BigDecimal qtyInPurchaseUnit;

    // Required Date
    private LocalDate requiredDate;

    // Purpose
    private String purpose;

}