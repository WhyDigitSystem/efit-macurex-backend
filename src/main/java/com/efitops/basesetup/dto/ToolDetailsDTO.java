package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ToolDetailsDTO {
    private String toolNo;
    private String toolName;
    private BigDecimal strokes;
    private BigDecimal strokesRate;
    private BigDecimal toolValue;
}