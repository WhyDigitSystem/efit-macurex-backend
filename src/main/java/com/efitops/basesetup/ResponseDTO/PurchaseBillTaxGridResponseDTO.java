// PurchaseBillTaxGridResponseDTO.java
package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseBillTaxGridResponseDTO {

    private Long id;
    private String particulars;
    private BigDecimal taxPercent;
    private BigDecimal acceptedQtyAmount;
    private BigDecimal revisedAmount;
    private ListOfVlauesDetailsResponseDTO ledgerAccount;
    private String debitCredit;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private Boolean postToFinanceAc;
}