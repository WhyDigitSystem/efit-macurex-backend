package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOrderAmendmentDetailsDTO {


    private Long item;

    private Long unit;

    private BigDecimal oldQty;

    private BigDecimal newQty;

    // getters and setters
}
