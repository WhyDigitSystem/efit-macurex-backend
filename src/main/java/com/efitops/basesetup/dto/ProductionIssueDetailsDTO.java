package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionIssueDetailsDTO {

    private Long id;
    private Long item;
    private Long unit;
    private BigDecimal availableQty;
    private String grnNo;
    private LocalDate grnDate;
    private BigDecimal intReqQty;
    private BigDecimal intPendQty;
    private BigDecimal issueQty;
    private BigDecimal itemMinQty;
    private BigDecimal rate;
    private BigDecimal amount;
}