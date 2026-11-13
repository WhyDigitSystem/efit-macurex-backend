package com.efitops.basesetup.dto;

import com.efitops.basesetup.entity.ItemMasterVO;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesContractDetailsDTO {

//    private Long id;

    private Long item;

    private String taxType;

    private Long taxPercentage;

    private Long unit;

    private BigDecimal quantity;

    private BigDecimal quotationRate;

    private BigDecimal orderRate;

    private BigDecimal discountPercentage;

    private LocalDate effectiveFrom;

    private LocalDate effectiveTo;

//    private BigDecimal discountAmount;
//
//    private BigDecimal amount;

//    private Long gstRate;
    
//    private BigDecimal sgstRate;
//
//    private BigDecimal sgstAmount;
//
//    private BigDecimal cgstRate;

//    private BigDecimal cgstAmount;

//    private BigDecimal igstRate;

//    private BigDecimal igstAmount;

    private String currency;

}
