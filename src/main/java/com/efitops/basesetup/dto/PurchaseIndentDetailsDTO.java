package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.entity.UnitMasterVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseIndentDetailsDTO {

//    private Long id;

    // Item
    private Long item;

    // Quantity
    private BigDecimal qtyInPrimaryUnit;

    // Conversion Factor (Dropdown)
    private Long conversionFactor;
    
    //unit
    private Long primaryUnit;
    private Long purchaseUnit;

    // Purchase Quantity
    private BigDecimal qtyInPurchaseUnit;

    // Required Date
    private LocalDate requiredDate;

    // Purpose
    private String purpose;

}