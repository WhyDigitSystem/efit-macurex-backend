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
public class MachineTechnicalInfoResponseDTO {

    private Long id;

    private LocalDate installationDate;

    private BigDecimal powerConsumption;

    private BigDecimal consumption;

    private BigDecimal powerProduced;

    private String technicalSpecification;

    private BigDecimal capacity;

    // Item Master
    private ItemResponse1DTO unit;

    private BigDecimal bedSizeMm;

    private BigDecimal currentInAmps;

    private BigDecimal voltage;

    private BigDecimal cushionTonnage;

    private BigDecimal parallelity;

    // List Of Values
    private ListOfValuesDetailsResponseDTO machineType;

    private BigDecimal hourlyRate;

    private BigDecimal machineInstrumentWeight;

    // Unit Master
    private UnitMasterResponseDTO uom;

    private LocalDate warrantyStartDate;

    private LocalDate warrantyEndDate;

    private LocalDate lastCalibratedDate;

    private LocalDate nextDueDate;

    private String lifeCycleYear;

    private String range;

    private String errorAllowed;

    private String frequencyOfCalibration;

    private BigDecimal instrumentCost;

    private BigDecimal calibrationCost;

    private String calibrationAgency;

    private String certificateNo;

    private BigDecimal shutHeightMm;

    private BigDecimal strokeMm;

    private BigDecimal cushion;

    private BigDecimal hp;

    private String hcNo;

    private BigDecimal rangeSize;

    private BigDecimal leastcount;

    private BigDecimal goSize;

    private BigDecimal noGoSize;

    private BigDecimal ramSize;

    private BigDecimal throatDepth;

    private BigDecimal throatGap;

    private LocalDate maintenanceDate;

}