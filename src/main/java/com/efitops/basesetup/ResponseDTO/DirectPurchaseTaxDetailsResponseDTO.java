package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectPurchaseTaxDetailsResponseDTO {

    private Long id;
    private String particulars;
    private BigDecimal tax;
    private BigDecimal acceptedQtyAmount;
    private BigDecimal revisedAmount;
    private String taxId;
}
