package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;

import com.efitops.basesetup.dto.ItemMasterResponseDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionScheduleForNextThreeMonthDetailsResponseDTO {

    private Long id;

    private ItemMasterResponseDetailsDTO item;

    private BigDecimal january;

    private BigDecimal february;

    private BigDecimal march;

    private BigDecimal april;

    private BigDecimal may;

    private BigDecimal june;

    private BigDecimal july;

    private BigDecimal august;

    private BigDecimal september;

    private BigDecimal october;

    private BigDecimal november;

    private BigDecimal december;
}