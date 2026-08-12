// PurchaseBillDetailsResponseDTO.java
package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.HsnResponseImageDTO;
import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;
import com.efitops.basesetup.dto.ListOfVlauesDetailsResponseDTO;
import com.efitops.basesetup.dto.PrimaryUnitImageDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseBillDetailsResponseDTO {

    private Long id;
    private ItemMasterResponseDetailsDTO itemCode;
    private HsnResponseImageDTO hsnCode;
    private ListOfVlauesDetailsResponseDTO taxType;
    private BigDecimal taxPercent;
    private String tariffNo;
    private Boolean exciseToPost;
    private BigDecimal challanQty;
    private PrimaryUnitImageDTO unit;
    private BigDecimal grnReceivedQty;
    private BigDecimal acceptedQty;
    private BigDecimal rejectedQty;
    private BigDecimal shortageQty;
    private BigDecimal poRate;
    private BigDecimal rateInInr;
    private BigDecimal rateInSelectedCurrency;
    private BigDecimal apportionedCost;
    private BigDecimal landedCostRate;
    private BigDecimal amount;
    private BigDecimal amountInSelectedCurrency;
    private BigDecimal additionalDuty;
    private BigDecimal amountInInr;
    private BigDecimal sgstRate;
    private BigDecimal sgstAmount;
    private BigDecimal cgstRate;
    private BigDecimal cgstAmount;
    private BigDecimal igstRate;
    private BigDecimal igstAmount;
}