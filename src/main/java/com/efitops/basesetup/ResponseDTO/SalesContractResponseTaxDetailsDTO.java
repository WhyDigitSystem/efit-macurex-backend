package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SalesContractResponseTaxDetailsDTO {

    private Long id;

    private ListOfValuesDetailsResponseDTO  particulars;

    private BigDecimal amount;
}

