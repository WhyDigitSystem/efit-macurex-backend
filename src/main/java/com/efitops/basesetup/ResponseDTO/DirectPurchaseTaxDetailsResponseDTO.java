// DirectPurchaseTaxDetailsResponseDTO.java
package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectPurchaseTaxDetailsResponseDTO {

    private Long id;
    private String particulars;
    private BigDecimal amount;
    private ListOfVlauesDetailsResponseDTO ledgerAccount;
}