package com.efitops.basesetup.dto;


import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapNoteReasonDetailsDTO {
    private Long id;
    private String reasonCode;
    private String reasonDescription;
    private BigDecimal rejQty;
}
