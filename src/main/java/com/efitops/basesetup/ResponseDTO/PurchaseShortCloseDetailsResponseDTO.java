package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.PrimaryUnitImageDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseShortCloseDetailsResponseDTO {

    private Long id;
    private ItemMasterResponseDetailsDTO itemCode; // carries item description too, same as other grids
    private PrimaryUnitImageDTO unit;

    private BigDecimal orderedQty;
    private BigDecimal suppliedQty;
    private BigDecimal pendingQty;
    private BigDecimal newRequiredQty;
    private BigDecimal shortCloseQty;
}