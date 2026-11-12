package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RejectionInvoiceTaxDetailsResponseDTO {

    private Long id;

    private String particulars;

    private BigDecimal acceptedQtyAmount;

    private BigDecimal revisedAmount;

}
