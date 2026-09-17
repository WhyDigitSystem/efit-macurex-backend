package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvForStoresDetailsDTO {

    private Long item;

    private Long unit;

    private BigDecimal bomQty;

    private BigDecimal issueQty;
}
