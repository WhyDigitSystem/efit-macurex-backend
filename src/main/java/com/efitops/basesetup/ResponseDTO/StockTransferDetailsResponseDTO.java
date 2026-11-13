package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTransferDetailsResponseDTO {

    private Long id;
    private ItemMasterDetailsResponseImportDTO item;
    private BigDecimal availableQty;
    private BigDecimal qty;
    private BigDecimal rate;
    private UnitMasterResponseDTO unit;
}