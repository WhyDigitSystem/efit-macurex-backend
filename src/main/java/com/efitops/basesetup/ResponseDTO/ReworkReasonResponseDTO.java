package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;
import lombok.Data;

@Data
public class ReworkReasonResponseDTO {
    private Long id;
    private String reason;
    private String reasonDescription;
    private BigDecimal qty;
    private BigDecimal timePerQty;
    private BigDecimal reworkProdHrs;
    private BigDecimal reworkMcCost;
    private BigDecimal reworkLabourCost;
}
