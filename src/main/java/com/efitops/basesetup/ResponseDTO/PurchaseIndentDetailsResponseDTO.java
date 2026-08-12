package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.ItemMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentDetailsResponseDTO {

    private Long id;

    // Full item master payload - same shape as /getItemMasterById response
    private ItemMasterResponseDTO itemMasterVO;

    private BigDecimal qtyInPrimaryUnit;
    private BigDecimal conversionFactor;
    private BigDecimal qtyInPurchaseUnit;
    private LocalDate requiredDate;
    private String purpose;
}