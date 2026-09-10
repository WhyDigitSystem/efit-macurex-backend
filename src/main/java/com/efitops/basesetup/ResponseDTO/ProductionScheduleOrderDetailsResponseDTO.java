package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionScheduleOrderDetailsResponseDTO {

    private Long id;
    
    private ItemMasterDetailsResponseImportDTO item;

    private BigDecimal bomQty;
    private BigDecimal qtyRequired;

    private UnitMasterResponseDTO unit;

    private BigDecimal scrapQty;

    private UnitMasterResponseDTO scrapUnit;
}