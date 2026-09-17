package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RmConsumptionEntryDetailsResponseDTO {

    private Long id;
    private ItemMasterDetailsResponseImportDTO item;
    private UnitResponseDTO unit;
    private BigDecimal consumptionAsPerBomQty;
    private BigDecimal availableStock;
    private BigDecimal actualConsumedQty;
    private BigDecimal wastageQty;
    private BigDecimal scrapQty;
    private BigDecimal totalConsumedQty;
    private BigDecimal rate;
    private BigDecimal amount;
}