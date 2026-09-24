package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionBulkIssuesDetailsDTO {

    private Long item;

    private Long unit;

    private BigDecimal availableQty;

    private BigDecimal indReqQty;

//    private BigDecimal indPendQty;

    private BigDecimal issueQty;

    private BigDecimal rate;

}