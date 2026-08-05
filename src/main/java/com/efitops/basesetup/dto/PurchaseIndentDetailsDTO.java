package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentDetailsDTO {



    private Long item; // item master id - itemCode/description/primaryUnit/purchaseUnit come from here

    private BigDecimal qtyInPrimaryUnit;

    private BigDecimal conversionFactor;

    private BigDecimal qtyInPurchaseUnit;

    private LocalDate requiredDate;

    private String purpose;
}