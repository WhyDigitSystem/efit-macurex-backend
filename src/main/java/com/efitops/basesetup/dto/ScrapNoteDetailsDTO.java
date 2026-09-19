package com.efitops.basesetup.dto;


import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapNoteDetailsDTO {
    private Long item;
    private Long primaryUnit;
    private BigDecimal stock;
    private BigDecimal quantity;
    private BigDecimal weight;
    private BigDecimal rate;
    private BigDecimal value;
}