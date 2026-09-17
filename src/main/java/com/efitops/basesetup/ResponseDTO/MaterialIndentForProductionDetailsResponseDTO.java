package com.efitops.basesetup.ResponseDTO;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialIndentForProductionDetailsResponseDTO {

    private Long id;
    private ItemMasterDetailsResponseImportDTO itemCode;
    private UnitResponseDTO unit;
    private BigDecimal schQty;
    private BigDecimal stockAvailable;
    private BigDecimal requiredQty;
}
