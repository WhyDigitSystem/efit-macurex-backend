package com.efitops.basesetup.ResponseDTO;


import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkIssueIndentDetailsResponseDTO {

    private Long id;

    private ItemResponse1DTO item;

    private BigDecimal reqQty;

    private UnitMasterResponseDTO unit;

    private LocalDate requiredDate;

    private String purpose;
}