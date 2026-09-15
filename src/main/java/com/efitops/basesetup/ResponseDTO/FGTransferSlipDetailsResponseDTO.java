package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FGTransferSlipDetailsResponseDTO {

    private Long id;
    private ItemMasterDetailsResponseImportDTO item;
    private UnitResponseDTO unit;
    private BigDecimal bomQty;
    private BigDecimal availableStock;
    private BigDecimal consumptionAsPerBom;
    private BigDecimal wastageQty;
    private BigDecimal consumedQty;
    private BigDecimal rate;
    private BigDecimal value;
    private String scrapId;
    private BigDecimal scrapQty;
    private UnitResponseDTO scrapUnit;
    private BigDecimal scrapTotal;
}