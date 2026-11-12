package com.efitops.basesetup.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineSpareDetailsDTO {

    private Long id;

    private Long spareId;

    private String spareDescription;

    private Long unit;

    private BigDecimal quantity;

    private boolean critical;

    private String modelNo;

    private String serialNo;

    private String manufacturer;

    private LocalDate warrantyTillDate;

    private String calibrationRequired;

    private LocalDate lastCalibratedDate;

}