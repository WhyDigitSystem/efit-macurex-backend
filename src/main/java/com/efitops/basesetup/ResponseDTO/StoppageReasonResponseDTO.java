package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class StoppageReasonResponseDTO {
    private Long id;
    private BigDecimal frTimeHrs;
    private BigDecimal frTimeMins;
    private BigDecimal toTimeHrs;
    private BigDecimal toTimeMins;
    private BigDecimal totTimeInMins;
    private String reason;
    private String description;
    private BigDecimal stoppageMcCost;
    private BigDecimal stoppageLabourCost;
    private String remarks;
}