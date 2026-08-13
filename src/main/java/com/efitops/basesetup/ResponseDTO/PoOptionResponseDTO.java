package com.efitops.basesetup.ResponseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoOptionResponseDTO {
    private Long poId;
    private String poNo;
    private LocalDate poDate;
    private String poType; // "PURCHASE_CONTRACT" or "LOCAL_PURCHASE_ORDER"
}