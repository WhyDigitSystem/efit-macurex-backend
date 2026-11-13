package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRateContractTaxDetailsResponseDTO {

    private Long id;

    private String particulars;

    private BigDecimal amount;

}
