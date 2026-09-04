package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuesDetailsDTO {

//    private Long id;

    private Long item;

    private BigDecimal qtyAvailable;

    private BigDecimal indentQty;

    private BigDecimal previouslyIssuedQty;

    private BigDecimal pendingQty;

    private BigDecimal qty;

    private BigDecimal rate;

//    private BigDecimal amount;
}