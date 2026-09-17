package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;

import com.efitops.basesetup.dto.CustomerResponseGstDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialTransferReturnNoteDetailsResponseDTO {

    private Long id;
    private BigDecimal availableQty;
    private BigDecimal qty;
    private BigDecimal rate;
    private BigDecimal value;
    private String reasonForRejectionTransfer;

    private ItemMasterDetailsResponseImportDTO item;
    private UnitResponseDTO unit;
    private CustomerResponseGstDetailsDTO supplier;
}