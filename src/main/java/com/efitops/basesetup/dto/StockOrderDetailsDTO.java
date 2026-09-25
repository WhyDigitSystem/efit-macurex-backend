package com.efitops.basesetup.dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockOrderDetailsDTO {

    private Long item;

    private Long unit;

    private BigDecimal requiredQty;

    private BigDecimal rate;
}
