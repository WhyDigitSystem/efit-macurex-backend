package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * "1-Contract Details" grid line.
 *
 * itemId        -> [USER SELECT] Item Code dropdown (ItemMasterVO). Item Description auto-fills from the
 *                   SAME item (itemMasterVO.getItemDescription()) — client does not send it separately.
 * hsnId         -> [AUTO] pulled from item.getHsnCode() when the item is picked; kept editable if user overrides.
 * taxType       -> [USER SELECT] List Of Values dropdown (e.g. SGST/CGST/IGST/EXEMPT)
 * taxDefinition -> [AUTO] resolved from TaxDefinition master for the chosen Tax Type; Tax(%) below is pulled from it
 * taxPercent    -> [AUTO, editable] copied from taxDefinition, user may override
 * unitId        -> [AUTO] defaults from item.getPrimaryUnit(); editable
 * rateInCurrency-> [USER ENTER]
 * sgstRate      -> [USER ENTER]
 * sgstAmount    -> [CALCULATED] server = rateInCurrency * sgstRate / 100
 * cgstRate      -> [USER ENTER]
 * cgstAmount    -> [CALCULATED] server = rateInCurrency * cgstRate / 100
 * igstRate      -> [USER ENTER]
 * igstAmount    -> [CALCULATED] server = rateInCurrency * igstRate / 100
 * validFrom     -> [USER ENTER, optional] defaults to header validFrom if left blank
 * validTo       -> [USER ENTER, optional] defaults to header validTo if left blank
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseContractDetailsDTO {



    private Long itemId;

    private Long hsnId;

    private Long taxType;

    private Long taxDefinition;

    private BigDecimal taxPercent;

    private Long unitId;

    private BigDecimal rateInCurrency;

    private BigDecimal sgstRate;

    private BigDecimal sgstAmount;

    private BigDecimal cgstRate;

    private BigDecimal cgstAmount;

    private BigDecimal igstRate;

    private BigDecimal igstAmount;

    private LocalDate validFrom;

    private LocalDate validTo;
}