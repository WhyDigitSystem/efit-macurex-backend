package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOrderAmendmentDetailsResponseDTO {

    private Long id;

    private ItemResponseDTO item;

    private UnitResponseDTO unit;

    private BigDecimal oldQty;

    private BigDecimal newQty;

}