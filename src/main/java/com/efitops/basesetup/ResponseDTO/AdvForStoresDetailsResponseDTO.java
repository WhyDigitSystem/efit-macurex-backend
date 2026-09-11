package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvForStoresDetailsResponseDTO {

    private Long id;

    private ItemResponseDTO item;

    private UnitMasterResponseDTO unit;

    private BigDecimal bomQty;

    private BigDecimal issueQty;
}