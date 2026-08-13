package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * itemId          -> [USER SELECT] Item Code; Description + Unit auto-fill from the same
 *                    ItemMaster record, sourced from the selected Indent's item lines
 * rateDifference  -> [USER SELECT] Yes/No toggle
 * qty             -> [USER ENTER]
 * rate            -> [USER ENTER]
 * amount          -> [CALCULATED] qty * rate
 * discount        -> [USER ENTER]
 * totalAmount     -> [CALCULATED] amount - discount
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectPurchaseDetailsDTO {

    private Long id;
    private Long itemId;
    private Boolean rateDifference;
    private BigDecimal qty;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal discount;
    private BigDecimal totalAmount;
}