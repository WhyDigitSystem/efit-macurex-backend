package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;

import lombok.Data;

@Data
public class ToolDetailsResponseDTO {
    private Long id;
    private ToolMasterResponseMasterDTO toolNo;
    private BigDecimal strokes;
    private BigDecimal strokesRate;
    private BigDecimal toolValue;
}
