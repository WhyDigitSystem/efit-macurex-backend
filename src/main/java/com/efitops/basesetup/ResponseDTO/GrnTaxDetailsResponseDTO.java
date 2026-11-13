package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrnTaxDetailsResponseDTO {

    private Long id;
    private String particulars;
    private BigDecimal tax;
    private BigDecimal taxVal;
    private BigDecimal taxAmount;
}
