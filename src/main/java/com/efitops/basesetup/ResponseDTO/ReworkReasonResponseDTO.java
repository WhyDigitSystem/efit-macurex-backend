package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;
import lombok.Data;

@Data
public class ReworkReasonResponseDTO {
    private Long id;
    private ReasonResponseDTO reason;
    private BigDecimal qty;
    private BigDecimal timePerQty;
    private BigDecimal reworkProdHrs;
    private BigDecimal reworkMcCost;
    private BigDecimal reworkLabourCost;
}
