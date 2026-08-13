package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * itemId              -> [USER SELECT] Item Code dropdown (ItemMasterVO)
 * (Item Description, HSN/SAC, Unit)  -> [AUTO] from the same item, same pattern as PurchaseContractDetailsDTO
 * taxType             -> [USER SELECT] List Of Values dropdown
 * taxPercent          -> [AUTO, editable] pulled from TaxDefinition for chosen Tax Type
 * tariffNo            -> [USER ENTER] ** or [AUTO] from item/HSN - CONFIRM
 * exciseToPost        -> [USER SELECT] Yes/No
 * challanQty          -> [USER ENTER]
 * grnReceivedQty      -> [USER ENTER] ** or [AUTO] from a GRN record - CONFIRM (depends on grnNo answer above)
 * acceptedQty         -> [USER ENTER]
 * rejectedQty         -> [USER ENTER]
 * shortageQty         -> [CALCULATED] challanQty - acceptedQty - rejectedQty ? ** CONFIRM formula, or user entered
 * poRate              -> [AUTO] from the selected PO (Purchase Contract detail line) ** CONFIRM
 * rateInInr           -> [USER ENTER] or [CALCULATED] poRate * exchangeRate ** CONFIRM
 * rateInSelectedCurrency -> [USER ENTER]
 * apportionedCost     -> [USER ENTER] or [CALCULATED] from Charges Summary allocation ** CONFIRM
 * landedCostRate      -> [CALCULATED] rateInInr + apportionedCost ? ** CONFIRM formula
 * amount              -> [CALCULATED] acceptedQty * rateInSelectedCurrency
 * amountInSelectedCurrency -> [CALCULATED]
 * additionalDuty      -> [USER ENTER]
 * amountInInr         -> [CALCULATED] amount * exchangeRate
 * sgstRate/cgstRate/igstRate -> [USER ENTER], amounts [CALCULATED] same pattern as PurchaseContractDetailsDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseBillDetailsDTO {

    private Long id;
    private Long itemId;

    private Long taxType;
    private BigDecimal taxPercent;

    private String tariffNo;
    private Boolean exciseToPost;

    private BigDecimal challanQty;
    private Long unitId;
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