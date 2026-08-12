package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReturnTaxDetailsResponseDto {

    private Long id;

    // Particulars
    private ListOfVlauesDetailsResponseDTO particulars;

    // Amount
    private BigDecimal amount;

}