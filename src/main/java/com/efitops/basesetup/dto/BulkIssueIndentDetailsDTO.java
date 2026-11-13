package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkIssueIndentDetailsDTO {


    private Long item;

    private BigDecimal reqQty;

    private Long unit;

    private LocalDate requiredDate;

    private String purpose;
}
