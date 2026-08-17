package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockTransferChallanTaxDetailsResponseDTO {

    private Long id;

    private Long particularsId;
    private String particularsCode;
    private String particularsDesc;

    private BigDecimal acceptQtyAmount;

    private BigDecimal revisedAmoount;
}