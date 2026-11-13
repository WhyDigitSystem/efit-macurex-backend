package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOrderDetailsResponseDTO {

    private Long id;

    private ItemMasterResponseDetailsDTO incomingItem;

//    private BomResponseDTO bom;

    private String bom;

    private UnitMasterResponseDTO unit;

    private String incomingType;

    private BigDecimal orderQty;

    private BigDecimal rate;

    private BigDecimal amount;

    private BigDecimal sgstRate;

    private BigDecimal sgstAmount;

    private BigDecimal cgstRate;

    private BigDecimal cgstAmount;

    private BigDecimal igstRate;

    private BigDecimal igstAmount;

    private String sentFor;

}
