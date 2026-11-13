package com.efitops.basesetup.dto;


import java.math.BigDecimal;

import lombok.Data;

@Data
public class StoppageReasonDTO {
    private BigDecimal frTimeHrs;
    private BigDecimal frTimeMins;
    private BigDecimal toTimeHrs;
    private BigDecimal toTimeMins;
    private BigDecimal totTimeInMins;
    private Long reason;
    private BigDecimal stoppageMcCost;
    private BigDecimal stoppageLabourCost;
    private String remarks;
}