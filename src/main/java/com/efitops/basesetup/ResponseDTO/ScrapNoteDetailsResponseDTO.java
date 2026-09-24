package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapNoteDetailsResponseDTO {
    private Long id;
    private BigDecimal stock;
    private BigDecimal quantity;
    private BigDecimal weight;
    private BigDecimal rate;
    private BigDecimal value;
    private ItemMasterDetailsResponseImportDTO item;
    private UnitResponseDTO primaryUnit;
}