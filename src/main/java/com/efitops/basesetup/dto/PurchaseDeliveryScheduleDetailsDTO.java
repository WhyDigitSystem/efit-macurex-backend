package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDeliveryScheduleDetailsDTO {

    private Long id;

    // [USER SELECT] - Primary Unit / Purchase Unit / Demand Qty / Available Stock / Qty
    // all auto-fill from this item server-side, client does not send them
    private Long itemId;

    // [USER ENTER]
    private BigDecimal tentativeQty;

    // [USER ENTER]
    private BigDecimal tentativeQtyNextMonth;

    // [USER ENTER]
    private BigDecimal rate;
}