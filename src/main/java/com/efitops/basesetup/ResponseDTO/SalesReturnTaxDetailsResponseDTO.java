package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReturnTaxDetailsResponseDTO {

    private Long id;

    private ListOfValuesDetailsResponseDTO particulars;

    private BigDecimal amount;

    private String glAccountName;
}
