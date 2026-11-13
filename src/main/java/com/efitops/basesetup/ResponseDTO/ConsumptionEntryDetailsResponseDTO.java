package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumptionEntryDetailsResponseDTO {

    private Long id;
    private ItemMasterDetailsResponseImportDTO item;
    private UnitResponseDTO unit;
    private BigDecimal consumedQty;
}