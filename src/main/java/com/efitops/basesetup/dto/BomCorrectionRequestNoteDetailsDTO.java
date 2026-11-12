package com.efitops.basesetup.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BomCorrectionRequestNoteDetailsDTO {


    private Long partNo;

    private Long unit;

    private BigDecimal bomQty;

    private String addedRemoved;
}