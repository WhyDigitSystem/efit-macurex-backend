package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesRejectionInvoiceTaxDetailsResponseDTO {

    private Long id;

    private ListOfValuesDetailsResponseDTO particulars;

    private String glAccountName;

    private BigDecimal acceptedQtyAmount;

    private BigDecimal revisedAmount;

    private BigDecimal amount;
}
