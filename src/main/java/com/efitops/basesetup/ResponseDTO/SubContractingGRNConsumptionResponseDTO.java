package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubContractingGRNConsumptionResponseDTO {

    private Long id;

    private ItemResponse1DTO outgoingItem;

    private UnitMasterResponseDTO unit;

    private String itemType;

    private BigDecimal bomQty;

    private BigDecimal availableStock;

    private BigDecimal consumedQty;

    private String scrapItem;

    private BigDecimal bomScrap;

    private BigDecimal scrapQty;

    private BigDecimal rate;

    private BigDecimal amount;
}