package com.efitops.basesetup.ResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.efitops.basesetup.dto.UnitMasterResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineSpareDetailsResponseDTO {

    private Long id;

    // Item Master
    private ItemResponse1DTO spareId;

    private String spareDescription;

    // Unit Master
    private UnitMasterResponseDTO unit;

    private BigDecimal quantity;

    private boolean critical;

    private String modelNo;

    private String serialNo;

    private String manufacturer;

    private LocalDate warrantyTillDate;

    private String calibrationRequired;

    private LocalDate lastCalibratedDate;
}